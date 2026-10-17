package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krakdev.jdbc.Conexion;
import com.krakdev.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {

    private static final Logger log = LoggerFactory.getLogger(VideojuegoJdbc.class);

    public static Videojuego insertar(Videojuego videojuego) {
        Connection con = null;

        try {
            con = Conexion.getConnection();
            String sql = """
                    INSERT INTO videojuegos(codigo, nombre, plataforma, precio, disponible, genero)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, videojuego.getCodigo());
            ps.setString(2, videojuego.getNombre());
            ps.setString(3, videojuego.getPlataforma());
            ps.setDouble(4, videojuego.getPrecio());
            ps.setBoolean(5, videojuego.isDisponible());
            ps.setString(6, videojuego.getGenero());

            int filas = ps.executeUpdate();
            log.info("Videojuego insertado, filas: " + filas);

            return videojuego;
        } catch (Exception e) {
            log.error("Error al insertar", e);
            throw new RuntimeException("Error al insertar " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Videojuego> listar() {
        List<Videojuego> videojuegos = new ArrayList<>();
        Connection con = null;

        try {
            con = Conexion.getConnection();
            String sql = "SELECT * FROM videojuegos";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Videojuego v = new Videojuego(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("plataforma"),
                        rs.getDouble("precio"),
                        rs.getBoolean("disponible"),
                        rs.getString("genero"));
                videojuegos.add(v);
            }

            log.info("Videojuegos listados: " + videojuegos.size());
        } catch (Exception e) {
            log.error("Error al listar", e);
            throw new RuntimeException("Error al listar " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return videojuegos;
    }

    public static Videojuego buscar(String codigo) {
        Connection con = null;
        Videojuego videojuego = null;

        try {
            con = Conexion.getConnection();
            String sql = "SELECT * FROM videojuegos WHERE codigo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                videojuego = new Videojuego(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("plataforma"),
                        rs.getDouble("precio"),
                        rs.getBoolean("disponible"),
                        rs.getString("genero"));
                log.info("Videojuego encontrado: " + codigo);
            } else {
                log.info("No existe un videojuego con código: " + codigo);
            }
        } catch (Exception e) {
            log.error("Error al buscar", e);
            throw new RuntimeException("Error al buscar " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return videojuego;
    }
}