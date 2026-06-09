package com.cashpoint.bck.servicios.Implementaxion;

import com.cashpoint.bck.excepcione.NoHayException;
import com.cashpoint.bck.persistencia.dtos.ProductoRequestDTO;
import com.cashpoint.bck.persistencia.dtos.ProductoResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.repositorios.CategoriaRepository;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.servicios.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO request) {
        CategoriaEntity categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new NoHayException("Categoria no existe :("));

        ProductoEntity producto = new ProductoEntity();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        producto.setActivo(true);

        return mapToDTO(productoRepository.save(producto));


    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NoHayException("Producto no existe ):"));
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO request) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoHayException("Producto no existe ):"));

        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());

        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoHayException("Producto no existe ):"));

        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoResponseDTO mapToDTO(ProductoEntity p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();

        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setCategoriaNombre(p.getCategoria().getNombre());
        return dto;
    }

}
