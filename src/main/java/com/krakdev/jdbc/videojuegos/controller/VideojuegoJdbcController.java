package com.krakdev.jdbc.videojuegos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.jdbc.videojuegos.services.ServicioVideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@RestController
@RequestMapping("/jdbc/videojuegos")
public class VideojuegoJdbcController {

    private final ServicioVideojuegoJdbc servicio;

    public VideojuegoJdbcController(ServicioVideojuegoJdbc servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Videojuego> crear(@RequestBody Videojuego videojuego) {
        Videojuego creado = servicio.crear(videojuego);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<Videojuego> listar() {
        return servicio.listar();
    }

    @GetMapping("/{codigo}")
    public Videojuego buscar(@PathVariable String codigo) {
        return servicio.buscarPorCodigo(codigo);
    }

    @PutMapping("/{codigo}")
    public Videojuego actualizar(@PathVariable String codigo, @RequestBody Videojuego videojuego) {
        return servicio.actualizar(codigo, videojuego);
    }

    @DeleteMapping("/{codigo}")
    public boolean eliminar(@PathVariable String codigo) {
        return servicio.eliminar(codigo);
    }
}