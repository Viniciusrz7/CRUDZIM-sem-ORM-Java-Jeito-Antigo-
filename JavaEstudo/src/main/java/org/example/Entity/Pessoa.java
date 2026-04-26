package org.example.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Pessoa {

    private Long id;
    private String nome;
    private int idade;
    private double peso;
    private LinkedHashSet<Produto> produtos = new LinkedHashSet<>();

    public Pessoa(Long id, String nome, int idade, double peso, LinkedHashSet<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        if (produtos != null) {
            this.produtos = produtos;
        }


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void addProdutos(Collection<Produto> novos) {
        this.produtos.addAll(novos);
    }

    public LinkedHashSet<Produto> getProdutos() {
        if (this.produtos == null) {
            this.produtos = new LinkedHashSet<>();
        }
        return produtos;
    }

    public void addProduto(Produto produto) {
        if (produto != null) {
            this.produtos.add(produto);
        }
    }

    public BigDecimal somarMeusProdutos() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (Produto produto : this.produtos) {
            if (produto.getValor() != null) {
                valorTotal = valorTotal.add(produto.getValor());
            }
        }
        return valorTotal;
    }

    public BigDecimal mediaValor() {
        BigDecimal MediaValor = BigDecimal.valueOf(0);
        BigDecimal soma = somarMeusProdutos();
        for (Produto produto : this.produtos) {
            MediaValor = soma.divide(BigDecimal.valueOf(produtos.size()), 2, RoundingMode.HALF_UP);
        }
        return MediaValor;
    }

//    public BigDecimal mediaValorStream() {
//        double media = this.produtos.stream()
//                .mapToDouble(p -> p.getValor().doubleValue())
//                .average()
//                .orElse(0.0);
//
//        return BigDecimal.valueOf(media);
//    }
//
//    public String primeiroProduto() {
//        return this.produtos.stream()
//                .findFirst()
//                .map(Produto::getNome)
//                .orElse("Nenhum produto");
//    }
}
