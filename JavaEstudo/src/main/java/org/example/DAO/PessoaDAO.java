package org.example.DAO;

import org.example.Connection.ConnectionDatabase;
import org.example.Entity.Pessoa;
import org.example.Entity.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class PessoaDAO implements GenericsDAO<Pessoa, Long> {

    @Override
    public void inserir(Pessoa entidade) {
        String sqlPessoa = "INSERT INTO pessoa (nome, idade, peso) VALUES (?, ?, ?)";
        String sqlRelacao = "INSERT INTO pessoa_produto (pessoa_id, produto_id) VALUES (?, ?)";

        try (Connection conn = ConnectionDatabase.getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtRelacao = conn.prepareStatement(sqlRelacao)) {

                stmtPessoa.setString(1, entidade.getNome());
                stmtPessoa.setInt(2, entidade.getIdade());
                stmtPessoa.setDouble(3, entidade.getPeso());
                stmtPessoa.executeUpdate();

                try (ResultSet rs = stmtPessoa.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidade.setId(rs.getLong(1));
                    }
                }

                for (Produto prod : entidade.getProdutos()) {
                    stmtRelacao.setLong(1, entidade.getId());
                    stmtRelacao.setLong(2, prod.getId());
                    stmtRelacao.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir", e);
        }
    }


    @Override
    public void remover(Long id) {
        String sqlRelacao = "DELETE FROM pessoa_produto WHERE pessoa_id = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conn = ConnectionDatabase.getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtRel = conn.prepareStatement(sqlRelacao);
                 PreparedStatement stmtPes = conn.prepareStatement(sqlPessoa)) {

                stmtRel.setLong(1, id);
                stmtRel.executeUpdate();

                stmtPes.setLong(1, id);
                stmtPes.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover pessoa", e);
        }
    }

    @Override
    public void atualizar(Pessoa entidade) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ?, peso = ? WHERE id = ?";
        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entidade.getNome());
            stmt.setInt(2, entidade.getIdade());
            stmt.setDouble(3, entidade.getPeso());
            stmt.setLong(4, entidade.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pessoa", e);
        }
    }

    @Override
    public Pessoa buscarPorId(Long id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        Pessoa pessoa = null;

        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pessoa = new Pessoa(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getDouble("peso"),
                            new LinkedHashSet<>()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa por ID", e);
        }
        return pessoa;
    }

    private List<Produto> buscarProdutosDaPessoa(Long pessoaId, Connection conn) throws SQLException {
        String sql = "SELECT p.* FROM produto p " +
                "JOIN pessoa_produto pp ON p.id = pp.produto_id " +
                "WHERE pp.pessoa_id = ?";

        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pessoaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto prod = new Produto(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getBigDecimal("valor")
                    );
                    produtos.add(prod);
                }
            }
        }
        return produtos;
    }

    @Override
    public List<Pessoa> buscarTodos() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa p = new Pessoa(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getDouble("peso"),
                        new LinkedHashSet<>()
                );

                p.addProdutos(buscarProdutosDaPessoa(p.getId(), conn));
                pessoas.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos", e);
        }
        return pessoas;
    }
}
