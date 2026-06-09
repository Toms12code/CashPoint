package com.cashpoint.bck.api;
import com.cashpoint.bck.persistencia.dtos.VentaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.VentaResponseDTO;
import com.cashpoint.bck.servicios.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crear(@RequestBody VentaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<VentaResponseDTO> anular(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.anular(id));
    }


}
