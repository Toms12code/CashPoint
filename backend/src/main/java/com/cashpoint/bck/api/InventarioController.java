package com.cashpoint.bck.api;


import com.cashpoint.bck.persistencia.dtos.MovimientoInvRequestDTO;
import com.cashpoint.bck.persistencia.dtos.MovimientoInvResponseDTO;
import com.cashpoint.bck.servicios.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    public ResponseEntity<MovimientoInvResponseDTO> entrada(@RequestBody MovimientoInvRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrarEntrada(request));
    }

    @PostMapping
    public ResponseEntity<MovimientoInvResponseDTO> salida(@RequestBody MovimientoInvRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrarSalida(request));
    }

    @GetMapping("/historial/{productoId}")
    public ResponseEntity<List<MovimientoInvResponseDTO>> historial(@PathVariable Long productoId) {
        return ResponseEntity.ok(inventarioService.historialPorProducto(productoId));
    }

    @GetMapping("/stock/{productoId}")
    public ResponseEntity<Integer> stockActual(@PathVariable Long productoId ) {
        return ResponseEntity.ok(inventarioService.stockActual(productoId));
    }

}
