package org.example;

import org.example.Entity.Pessoa;
import org.example.Entity.Produto;
import org.example.Service.PessoaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.*;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        Produto produto1 = new Produto(1L,"Borracha",new BigDecimal(30));
//        Produto produto2 = new Produto(2L,"test",new BigDecimal(40));
//        Produto produto3 = new Produto(3L,"test2",new BigDecimal(70));
//        Produto produto4 = new Produto(4L,"test3",new BigDecimal(80));
//        Produto produto5 = new Produto(5L,"test4",new BigDecimal(999));
//        Pessoa pessoa = new Pessoa(1L,"aluno",13,13,new LinkedHashSet<>(List.of(produto1, produto2, produto3, produto4)));
//        pessoa.getProdutos().stream()
//                .filter(p -> p.getNome().equals("test"))
//                .forEach(p -> System.out.println("Encontrado: " + p.getNome()));
//
//        pessoa.addProduto(produto5);
//        System.out.println("Total de produtos agora: " + pessoa.getProdutos().size());
//        System.out.println("Nome do último produto: " + pessoa.getProdutos().stream().toList().get(pessoa.getProdutos().size() - 1).getNome());
//
//        BigDecimal valorTotal = pessoa.somarMeusProdutos();
//        System.out.println("O valor Total é " + valorTotal);
//
//        BigDecimal media = pessoa.mediaValor();
//        System.out.println("A media é " + media);
//
//        String primeiroproduto = pessoa.primeiroProduto();
//        System.out.println("O primeiro produto " + primeiroproduto);
//        PessoaService service = new PessoaService();
//        try {
//            // 1. Criando novos produtos com IDs que não usamos antes (50, 51)
//            Produto prodA = new Produto(50L, "Smartphone", new BigDecimal("1500.00"));
//            Produto prodB = new Produto(51L, "Fone Bluetooth", new BigDecimal("200.00"));
//
//            // 2. Criando uma nova pessoa (ID 5)
//            Pessoa p = new Pessoa(5L, "Lucas Dev", 30, 80.0, new LinkedHashSet<>());
//            p.addProduto(prodA);
//            p.addProduto(prodB);
//
//            // 3. Salvando
//            service.salvarPessoaComValidacao(p);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(Main.class, args);
    }
}