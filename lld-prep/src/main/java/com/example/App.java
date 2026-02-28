package com.example;

import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class App {
    public static void main(String[] args) throws SQLException {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/pradeep");
        config.setUsername("pradeep");
        config.setPassword("admin123");

        // Pool settings
        config.setMaximumPoolSize(2);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setPoolName("pradeepPool");
        HikariDataSource dataSource = new HikariDataSource(config);

        for(int i = 0; i < 5; i++){
            try(Connection connection = dataSource.getConnection()){
                System.out.println("connection instance" + connection);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Query all tables
        String query = """
                SELECT * FROM public.products
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            System.out.println("Tables in database:");

            // Iterate over ResultSet
            while (rs.next()) {
                String schema = rs.getString("product_id");
                System.out.println(schema + "." + schema);
            }
        }

        dataSource.close();
    }
}