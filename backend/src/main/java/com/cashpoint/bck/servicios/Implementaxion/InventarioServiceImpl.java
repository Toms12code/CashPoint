package com.cashpoint.bck.servicios.Implementaxion;

import com.cashpoint.bck.excepcione.NoHayException;
import com.cashpoint.bck.persistencia.entidades.MovimientoInvEntity;
import com.cashpoint.bck.persistencia.entidades.enums.*;
import com.cashpoint.bck.persistencia.dtos.MovimientoInvRequestDTO;
import com.cashpoint.bck.persistencia.dtos.MovimientoInvResponseDTO;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.repositorios.MovimientoInvRepository;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.servicios.InventarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final MovimientoInvRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public InventarioServiceImpl(MovimientoInvRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    @Override
    public MovimientoInvResponseDTO registrarEntrada(MovimientoInvRequestDTO request) {
        ProductoEntity producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new NoHayException("Producto no encontrado"));

        producto.setStock(producto.getStock() + request.getCantidad());
        productoRepository.save(producto);

        return guardarMovimiento(producto, request, TipoMovimiento.ENTRADA);
    }

    @Override
    @Transactional
    public MovimientoInvResponseDTO registrarSalida(MovimientoInvRequestDTO request) {
        ProductoEntity producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new NoHayException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - request.getCantidad());
        productoRepository.save(producto);

        return guardarMovimiento(producto, request, TipoMovimiento.SALIDA);
    }

    @Override
    public List<MovimientoInvResponseDTO> historialPorProducto(Long productoId) {
        return movimientoRepository.findByProductoId(productoId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public Integer stockActual(Long productoId) {
        ProductoEntity producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new NoHayException("Producto no encontrado"));
        return producto.getStock();
    }

    private MovimientoInvResponseDTO guardarMovimiento(ProductoEntity producto, MovimientoInvRequestDTO request, TipoMovimiento tipo) {
        MovimientoInvEntity movimiento = new MovimientoInvEntity();
        movimiento.setProducto(producto);
        movimiento.setCantidad(request.getCantidad());
        movimiento.setEstado(tipo);
        movimiento.setFecha(LocalDate.now());
        movimiento.setMotivo(request.getMotivo());

        return mapToDTO(movimientoRepository.save(movimiento));
    }

    private MovimientoInvResponseDTO mapToDTO(MovimientoInvEntity m) {
        MovimientoInvResponseDTO dto = new MovimientoInvResponseDTO();
        dto.setId(m.getId());
        dto.setProductoId(m.getProducto().getId());
        dto.setProductoNombre(m.getProducto().getNombre());
        dto.setCantidad(m.getCantidad());
        dto.setTipo(m.getEstado().name());
        dto.setFecha(LocalDateTime.now());
        dto.setMotivo(m.getMotivo());
        return dto;
    }
}