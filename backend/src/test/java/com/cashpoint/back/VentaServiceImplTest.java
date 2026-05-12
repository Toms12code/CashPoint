package com.cashpoint.back;

import com.cashpoint.back.persistencia.entidades.enums.EstadoVenta;
import com.cashpoint.bck.persistencia.dtos.DetalleVentaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.VentaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.VentaResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.entidades.DetalleVentaEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.entidades.VentaEntity;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.persistencia.repositorios.VentaRepository;
import com.cashpoint.bck.servicios.Implementaxion.VentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private ProductoEntity productoFake;

    @BeforeEach
    void setUp() {
        productoFake = new ProductoEntity();
        productoFake.setId(1L);
        productoFake.setNombre("Agua");
        productoFake.setPrecio(1500.0);
        productoFake.setStock(100);

        CategoriaEntity cat = new CategoriaEntity();
        cat.setNombre("Bebidas");
        productoFake.setCategoria(cat);
    }

    @Test
    void crear_deberiaCrearVenta_cuandoStockSuficiente() {

        DetalleVentaRequestDTO detalle = new DetalleVentaRequestDTO();
        detalle.setProductoId(1L);
        detalle.setCantidad(2);

        VentaRequestDTO request = new VentaRequestDTO();
        request.setDetalles(List.of(detalle));

        VentaEntity ventaGuardada = new VentaEntity();
        ventaGuardada.setId(1L);
        ventaGuardada.setTotal(3000.0);
        ventaGuardada.setEstado(EstadoVenta.ACTIVA);
        ventaGuardada.setFecha(LocalDateTime.now());
        ventaGuardada.setDetalles(new ArrayList<>());

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));
        when(ventaRepository.save(any(VentaEntity.class))).thenReturn(ventaGuardada);


        VentaResponseDTO resultado = ventaService.crear(request);


        assertNotNull(resultado);
        assertEquals(EstadoVenta.ACTIVA, resultado.getEstado());
    }

    @Test
    void crear_deberiaLanzarExcepcion_cuandoStockNoSuficiente() {
        productoFake.setStock(1);

        DetalleVentaRequestDTO detalle = new DetalleVentaRequestDTO();
        detalle.setProductoId(1L);
        detalle.setCantidad(10);

        VentaRequestDTO request = new VentaRequestDTO();
        request.setDetalles(List.of(detalle));

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));

        assertThrows(RuntimeException.class, () -> ventaService.crear(request));
    }

    @Test
    void anular_deberiaAnularVenta_cuandoEstaActiva() {
        DetalleVentaEntity detalle = new DetalleVentaEntity();
        detalle.setProducto(productoFake);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(1500.0);

        VentaEntity venta = new VentaEntity();
        venta.setId(1L);
        venta.setEstado(com.cashpoint.back.persistencia.entidades.enums.EstadoVenta.ACTIVA);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(3000.0);
        venta.setDetalles(List.of(detalle));

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(VentaEntity.class))).thenReturn(venta);

        VentaResponseDTO resultado = ventaService.anular(1L);

        assertNotNull(resultado);
        verify(ventaRepository).save(any(VentaEntity.class));
    }

    @Test
    void anular_deberiaLanzarExcepcion_cuandoYaEstaAnulada() {
        VentaEntity venta = new VentaEntity();
        venta.setId(1L);
        venta.setEstado(EstadoVenta.ANULADA);
        venta.setDetalles(new ArrayList<>());

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        assertThrows(RuntimeException.class, () -> ventaService.anular(1L));
    }

    @Test
    void listar_deberiaRetornarListaDeDTO() {
        VentaEntity venta = new VentaEntity();
        venta.setId(1L);
        venta.setEstado(EstadoVenta.ACTIVA);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(3000.0);
        venta.setDetalles(new ArrayList<>());

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<VentaResponseDTO> resultado = ventaService.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ventaService.obtenerPorId(99L));
    }
}