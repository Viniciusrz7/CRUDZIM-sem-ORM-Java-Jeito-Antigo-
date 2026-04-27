package org.example.Connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDatabase {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConnectionDatabase.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            System.err.println("Aviso: Arquivo application.properties não encontrado. Usando variáveis de ambiente.");
        }
    }

    public static Connection getConexao() throws SQLException {
        String url = System.getenv("SPRING_DATASOURCE_URL");
        String user = System.getenv("SPRING_DATASOURCE_USERNAME");
        String pass = System.getenv("SPRING_DATASOURCE_PASSWORD");

        if (url == null || url.isEmpty()) {
            url = props.getProperty("db.url");
            user = props.getProperty("db.usuario");
            pass = props.getProperty("db.senha");
        }

        System.out.println("Conectando ao banco: " + url);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
    }
}