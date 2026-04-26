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
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar as propriedades", e);
        }
    }
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.usuario"),
                props.getProperty("db.senha")
        );
    }
}
