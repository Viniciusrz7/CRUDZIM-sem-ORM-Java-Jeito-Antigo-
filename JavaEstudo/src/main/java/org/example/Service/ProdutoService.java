package org.example.Service;

import org.example.DAO.ProdutoDAO;
import org.example.Entity.Produto;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void salvarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do produto não pode estar vazio.");
        }

        if (produto.getValor() == null || produto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do produto deve ser maior que zero.");
        }
        if (produto.getId() != null && produto.getId() > 0) {
            if (produtoDAO.buscarPorId(produto.getId()) != null) {
                produtoDAO.atualizar(produto);
                return;
            }
        }
        produtoDAO.inserir(produto);
    }

    public List<Produto> listarTudo() {
        return produtoDAO.buscarTodos();
    }

    public void excluirProduto(Long id) {
        if (id == null) {
            throw new RuntimeException("ID inválido.");
        }
        produtoDAO.remover(id);
    }
}
