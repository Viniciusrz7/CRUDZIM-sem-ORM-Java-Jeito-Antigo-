package org.example.Service;

import org.example.DAO.PessoaDAO;
import org.example.DAO.ProdutoDAO;
import org.example.DTO.PessoaDTO;
import org.example.Entity.Pessoa;
import org.example.Entity.Produto;

import java.util.List;


public class PessoaService {

    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void salvarPessoaComValidacao(Pessoa pessoa, List<Long> produtosIds) {
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new RuntimeException("O nome é obrigatório!");
        }

        if (produtosIds != null) {
            produtosIds.forEach(id -> pessoa.addProduto(new Produto(id, null, null)));
        }

        if (pessoa.getId() != null && pessoa.getId() > 0) {
            pessoaDAO.atualizar(pessoa);
        } else {
            pessoaDAO.inserir(pessoa);
        }
    }
    public Pessoa buscarPessoaCompleta(Long id) {
        if (id == null) return null;
        return pessoaDAO.buscarPorId(id);
    }

    public PessoaDTO buscarPessoaParaExibicao(Long id) {
        Pessoa pessoa = pessoaDAO.buscarPorId(id);

        if (pessoa != null) {
            return new PessoaDTO(
                    pessoa.getId(),
                    pessoa.getNome(),
                    pessoa.somarMeusProdutos(),
                    pessoa.mediaValor()
            );
        }
        return null;
    }

    public List<PessoaDTO> buscarTodasPessoasParaExibicao() {
        List<Pessoa> pessoas = pessoaDAO.buscarTodos();
        return pessoas.stream()
                .filter(p -> p != null && p.getNome() != null && !p.getNome().isBlank())
                .map(p -> new PessoaDTO(
                        p.getId(),
                        p.getNome(),
                        p.somarMeusProdutos(),
                        p.mediaValor()
                ))
                .toList();
    }

}
