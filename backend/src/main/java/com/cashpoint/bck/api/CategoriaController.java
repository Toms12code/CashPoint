package com.cashpoint.bck.api;

import com.cashpoint.bck.persistencia.dtos.CategoriaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.CategoriaResponseDTO;
import com.cashpoint.bck.servicios.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
        public ResponseEntity<CategoriaResponseDTO> crear(@RequestBody CategoriaRequestDTO request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(request));
        }

        @GetMapping
        public ResponseEntity<List<CategoriaResponseDTO>> listar() {
            return ResponseEntity.ok(categoriaService.listar());
        }

        @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
