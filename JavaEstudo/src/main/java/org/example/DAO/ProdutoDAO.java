package org.example.DAO;

import org.example.Connection.ConnectionDatabase;
import org.example.Entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements GenericsDAO<Produto, Long> {

    @Override
    public void inserir(Produto p) {
        String sql = "INSERT INTO produto (nome, valor) VALUES (?, ?)";
        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setBigDecimal(2, p.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto", e);
        }
    }

    @Override
    public void remover(Long id) {
        String sqlRelacao = "DELETE FROM pessoa_produto WHERE produto_id = ?";
        String sqlProduto = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionDatabase.getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtRel = conn.prepareStatement(sqlRelacao);
                 PreparedStatement stmtProd = conn.prepareStatement(sqlProduto)) {

                stmtRel.setLong(1, id);
                stmtRel.executeUpdate();
                stmtProd.setLong(1, id);
                stmtProd.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover produto", e);
        }
    }

    @Override
    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET nome = ?, valor = ? WHERE id = ?";

        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setBigDecimal(2, p.getValor());
            stmt.setLong(3, p.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }

    @Override
    public Produto buscarPorId(Long id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getBigDecimal("valor")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID", e);
        }
        return null;
    }

    @Override
    public List<Produto> buscarTodos() {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionDatabase.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("valor")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos", e);
        }
        return produtos;
    }
}
