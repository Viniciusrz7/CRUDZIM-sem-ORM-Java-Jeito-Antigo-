package org.example.DTO;

import java.math.BigDecimal;

public record PessoaDTO(Long id,
                        String nome,
                        BigDecimal totalProdutos,
                        BigDecimal mediaPreco) {
}
