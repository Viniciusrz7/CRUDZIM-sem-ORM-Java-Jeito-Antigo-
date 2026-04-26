create DATABASE estudoJava;

use estudoJava;

CREATE TABLE pessoa (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT,
    peso DOUBLE
);


CREATE TABLE produto (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(19, 2) -- Decimal é o ideal para o BigDecimal do Java
);


CREATE TABLE pessoa_produto (
    pessoa_id BIGINT,
    produto_id BIGINT,
    PRIMARY KEY (pessoa_id, produto_id), -- Garante que não haja duplicatas iguais
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);
