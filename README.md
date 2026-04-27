🚀 CRUD Java: O "Jeito Antigo" (Sem ORM)
Este projeto é um sistema de gestão de Pessoas e Produtos desenvolvido com Java 17 e Spring Boot, utilizando o padrão JDBC puro (sem o uso de frameworks ORM como Hibernate ou Spring Data JPA). O objetivo principal é demonstrar o domínio sobre a manipulação manual de conexões, instruções SQL e o padrão DAO (Data Access Object).

O sistema conta com uma interface moderna inspirada no Figma, utilizando Tailwind CSS para uma experiência de utilizador fluida e responsiva.

##Link de Deploy
https://crud-java-vinicius-jeitoantigo.onrender.com/

🛠 Tecnologias Utilizadas
Backend: Java 17, Spring Boot 3.2.0.

Acesso a Dados: JDBC (Java Database Connectivity) com MySQL Connector.

Frontend: Thymeleaf + Tailwind CSS.

Base de Dados: MySQL (Hospedado no Aiven Cloud).

Containerização: Docker.

Deploy: Render.

🏗 Arquitetura do Projeto
O projeto segue uma estrutura de camadas para garantir a separação de responsabilidades:

Controller: Gere as rotas HTTP e a comunicação com o frontend.

Service: Contém a lógica de negócio e as regras de validação.

DAO (Data Access Object): Camada de persistência que executa os comandos SQL manualmente via JDBC.

Connection: Gestão centralizada da conexão com a base de dados utilizando variáveis de ambiente para segurança.

🚀 Como Executar o Projeto
Pré-requisitos
Java 17 instalado.

MySQL 8.0 ou superior.

Maven.

1. Configuração da Base de Dados
Execute o seguinte script SQL para criar a estrutura necessária:

SQL
CREATE DATABASE IF NOT EXISTS defaultdb;
USE defaultdb;

CREATE TABLE pessoa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT NOT NULL,
    peso DECIMAL(10, 2) NOT NULL
);

CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(19, 2) NOT NULL
);

CREATE TABLE pessoa_produto (
    pessoa_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    PRIMARY KEY (pessoa_id, produto_id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);
2. Variáveis de Ambiente
Para rodar a aplicação, configure as seguintes variáveis de ambiente (ou edite o application.properties para uso local):

SPRING_DATASOURCE_URL: jdbc:mysql://<HOST>:<PORT>/defaultdb

SPRING_DATASOURCE_USERNAME: seu_usuario

SPRING_DATASOURCE_PASSWORD: sua_senha

3. Execução
No terminal, dentro da pasta raiz do projeto:

Bash
mvn clean package
java -jar target/JavaEstudo-1.0-SNAPSHOT.jar
🐳 Dockerização
O projeto possui um Dockerfile multi-estágio para otimização do build:

Dockerfile
# Build da aplicação
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Execução
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/JavaEstudo-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
🎨 Interface
A interface foi projetada para ser limpa e profissional, utilizando:

Cards interativos para navegação.

Estados de hover e transições suaves.

Tipografia "Inter" focada em legibilidade.

Desenvolvido por Vinícius Reis Zimmermann como parte dos estudos de Engenharia de Software.
