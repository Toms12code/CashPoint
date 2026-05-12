package com.cashpoint.back;

import com.cashpoint.bck.persistencia.dtos.MovimientoInvRequestDTO;
import com.cashpoint.bck.persistencia.dtos.MovimientoInvResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.entidades.MovimientoInvEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.entidades.enums.TipoMovimiento;
import com.cashpoint.bck.persistencia.repositorios.MovimientoInvRepository;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.servicios.Implementaxion.InventarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceImplTest {

    @Mock
    private MovimientoInvRepository movimientoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    private ProductoEntity productoFake;

    @BeforeEach
    void setUp() {
        productoFake = new ProductoEntity();
        productoFake.setId(1L);
        productoFake.setNombre("Agua");
        productoFake.setStock(50);

        CategoriaEntity cat = new CategoriaEntity();
        cat.setNombre("Bebidas");
        productoFake.setCategoria(cat);
    }

    @Test
    void registrarEntrada_deberiaAumentarStock() {
        MovimientoInvRequestDTO request = new MovimientoInvRequestDTO();
        request.setProductoId(1L);
        request.setCantidad(20);
        request.setMotivo("Reabastecimiento");

        MovimientoInvEntity movimiento = new MovimientoInvEntity();
        movimiento.setId(1L);
        movimiento.setProducto(productoFake);
        movimiento.setCantidad(20);
        movimiento.setEstado(TipoMovimiento.ENTRADA);
        movimiento.setFecha(LocalDate.from(LocalDateTime.now()));

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));
        when(movimientoRepository.save(any())).thenReturn(movimiento);

        MovimientoInvResponseDTO resultado = inventarioService.registrarEntrada(request);

        assertNotNull(resultado);
        assertEquals("ENTRADA", resultado.getTipo());
        verify(productoRepository).save(productoFake);
    }

    @Test
    void registrarSalida_deberiaLanzarExcepcion_cuandoStockInsuficiente() {
        MovimientoInvRequestDTO request = new MovimientoInvRequestDTO();
        request.setProductoId(1L);
        request.setCantidad(100);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));

        assertThrows(RuntimeException.class, () -> inventarioService.registrarSalida(request));
    }

    @Test
    void registrarSalida_deberiaDisminuirStock_cuandoStockSuficiente() {
        MovimientoInvRequestDTO request = new MovimientoInvRequestDTO();
        request.setProductoId(1L);
        request.setCantidad(10);
        request.setMotivo("Venta manual");

        MovimientoInvEntity movimiento = new MovimientoInvEntity();
        movimiento.setId(1L);
        movimiento.setProducto(productoFake);
        movimiento.setCantidad(10);
        movimiento.setEstado(TipoMovimiento.SALIDA);
        movimiento.setFecha(LocalDate.from(LocalDateTime.now()));

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));
        when(movimientoRepository.save(any())).thenReturn(movimiento);

        MovimientoInvResponseDTO resultado = inventarioService.registrarSalida(request);

        assertEquals("SALIDA", resultado.getTipo());
    }

    @Test
    void historialPorProducto_deberiaRetornarLista() {
        MovimientoInvEntity m = new MovimientoInvEntity();
        m.setId(1L);
        m.setProducto(productoFake);
        m.setCantidad(10);
        m.setEstado(TipoMovimiento.ENTRADA);
        m.setFecha(LocalDate.from(LocalDateTime.now()));

        when(movimientoRepository.findByProductoId(1L)).thenReturn(List.of(m));

        List<MovimientoInvResponseDTO> resultado = inventarioService.historialPorProducto(1L);

        assertEquals(1, resultado.size());
    }

    @Test
    void stockActual_deberiaRetornarStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));

        Integer stock = inventarioService.stockActual(1L);

        assertEquals(50, stock);
    }

    @Test
    void stockActual_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventarioService.stockActual(99L));
    }
}