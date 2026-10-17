package com.krakdev.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conexion {

    private static final Logger log = LoggerFactory.getLogger(Conexion.class);
    private static final String URL = "jdbc:postgresql://localhost:5432/apijdbc";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2006"; 

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            log.info("Conexión realizada");
            return con;
        } catch (SQLException e) {
            log.error("Error al conectar con PostgreSQL", e);
            throw new RuntimeException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}