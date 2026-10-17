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
        PreparedStatement ps = null;

        try {
            con = Conexion.getConnection();
            String sql = """
                    INSERT INTO videojuegos(codigo, nombre, plataforma, precio, disponible, genero)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            ps = con.prepareStatement(sql);
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
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar conexión en insertar", e);
            }
        }
    }

    public static List<Videojuego> listar() {
        List<Videojuego> videojuegos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConnection();
            String sql = "SELECT * FROM videojuegos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar conexión en listar", e);
            }
        }
        return videojuegos;
    }

    public static Videojuego buscar(String codigo) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Videojuego videojuego = null;

        try {
            con = Conexion.getConnection();
            String sql = "SELECT * FROM videojuegos WHERE codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();

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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar conexión en buscar", e);
            }
        }
        return videojuego;
    }

    public static Videojuego actualizar(String codigo, Videojuego videojuego) {
        Connection con = null;
        PreparedStatement ps = null;
        Videojuego actualizado = null;

        try {
            con = Conexion.getConnection();
            String sql = """
                    UPDATE videojuegos
                    SET nombre = ?, plataforma = ?, precio = ?, disponible = ?, genero = ?
                    WHERE codigo = ?
                    """;
            ps = con.prepareStatement(sql);
            ps.setString(1, videojuego.getNombre());
            ps.setString(2, videojuego.getPlataforma());
            ps.setDouble(3, videojuego.getPrecio());
            ps.setBoolean(4, videojuego.isDisponible());
            ps.setString(5, videojuego.getGenero());
            ps.setString(6, codigo);

            int filas = ps.executeUpdate();
            log.info("Videojuego actualizado, filas: " + filas);

            if (filas > 0) {
                actualizado = new Videojuego(codigo, videojuego.getNombre(), videojuego.getPlataforma(),
                        videojuego.getPrecio(), videojuego.isDisponible(), videojuego.getGenero());
            }
        } catch (Exception e) {
            log.error("Error al actualizar", e);
            throw new RuntimeException("Error al actualizar " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar conexión en actualizar", e);
            }
        }
        return actualizado;
    }

    public static boolean eliminar(String codigo) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean eliminado = false;

        try {
            con = Conexion.getConnection();
            String sql = "DELETE FROM videojuegos WHERE codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);

            int filas = ps.executeUpdate();
            log.info("Videojuego eliminado, filas: " + filas);

            eliminado = filas > 0;
        } catch (Exception e) {
            log.error("Error al eliminar", e);
            throw new RuntimeException("Error al eliminar " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Error al cerrar conexión en eliminar", e);
            }
        }
        return eliminado;
    }
}