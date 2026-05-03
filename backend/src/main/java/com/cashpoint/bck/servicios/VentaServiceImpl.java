package com.cashpoint.bck.servicios;

import com.cashpoint.back.persistencia.entidades.enums.EstadoVenta;
import com.cashpoint.bck.persistencia.dtos.*;
import com.cashpoint.bck.persistencia.entidades.DetalleVentaEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.entidades.VentaEntity;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.persistencia.repositorios.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaServiceImpl(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public VentaResponseDTO crear(VentaRequestDTO request) {
        VentaEntity venta = new VentaEntity();
        venta.setFecha(LocalDateTime.now());
        venta.setEstado(EstadoVenta.ACTIVA);

        double total = 0.0;
        for (DetalleVentaRequestDTO item : request.getDetalles()) {
            ProductoEntity producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe :/"));
            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("manito no queda " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetalleVentaEntity detalle = new DetalleVentaEntity();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);
            total += producto.getPrecio() * item.getCantidad();
        }

        venta.setTotal(total);
        return mapToDTO(ventaRepository.save(venta));
    }
    @Override
    public List<VentaResponseDTO> listar() {
        return ventaRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Override
    public VentaResponseDTO obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Venta no se encontro >:$"));
    }

    @Transactional
    @Override
    public VentaResponseDTO anular(Long id) {
        VentaEntity venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no existe D:"));

        if(venta.getEstado() == EstadoVenta.ANULADA) {
            throw new RuntimeException("La venta is already anulada");
        }
        for(DetalleVentaEntity detalle : venta.getDetalles()) {
            ProductoEntity producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        }
        venta.setEstado(EstadoVenta.ANULADA);
        return mapToDTO(ventaRepository.save(venta));
    }
    private VentaResponseDTO mapToDTO(VentaEntity v) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(v.getId());
        dto.setFecha(v.getFecha());
        dto.setTotal(v.getTotal());
        dto.setEstado(v.getEstado());

        List<DetalleVentaResponseDTO> detalles = v.getDetalles().stream()
                .map(d -> {
                    DetalleVentaResponseDTO dDto = new DetalleVentaResponseDTO();
                    dDto.setId(d.getId());
                    dDto.setProductoNombre(d.getProducto().getNombre());
                    dDto.setCantidad(d.getCantidad());
                    dDto.setPrecioUnitario(d.getPrecioUnitario());
                    dDto.setSubtotal(d.getCantidad() * d.getPrecioUnitario());
                    return dDto;
                })
                .toList();
        dto.setDetalles(detalles);
        return dto;
    }


}

