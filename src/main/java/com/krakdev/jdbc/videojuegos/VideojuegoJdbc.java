package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.jdbc.Conexion;
import com.krakdev.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {

    private static final Logger log = LoggerFactory.getLogger(VideojuegoJdbc.class);

    public static Videojuego insertar(Videojuego videojuego) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = """
                INSERT INTO videojuegos(codigo, nombre, plataforma, precio, disponible, genero)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, videojuego.getCodigo());
            ps.setString(2, videojuego.getNombre());
            ps.setString(3, videojuego.getPlataforma());
            ps.setDouble(4, videojuego.getPrecio());
            ps.setBoolean(5, videojuego.isDisponible());
            ps.setString(6, videojuego.getGenero());

            int filas = ps.executeUpdate();
            log.info("Videojuego insertado: " + videojuego.getCodigo() + " (filas: " + filas + ")");

            return videojuego;
        } catch (Exception e) {
            log.error("Error al insertar", e);
            throw new RuntimeException("Error al insertar " + e.getMessage());
        } finally {
            cerrar(con, ps);
        }
    }

    private static void cerrar(Connection con, PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            log.error("Error al cerrar conexión", e);
        }
    }
}