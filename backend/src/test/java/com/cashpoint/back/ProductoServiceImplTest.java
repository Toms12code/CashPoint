package com.cashpoint.back;

import com.cashpoint.bck.persistencia.dtos.ProductoRequestDTO;
import com.cashpoint.bck.persistencia.dtos.ProductoResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import com.cashpoint.bck.persistencia.repositorios.CategoriaRepository;
import com.cashpoint.bck.persistencia.repositorios.ProductoRepository;
import com.cashpoint.bck.servicios.Implementaxion.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private CategoriaEntity categoriaFake;
    private ProductoEntity productoFake;

    @BeforeEach
    void setUp() {
        categoriaFake = new CategoriaEntity();
        categoriaFake.setId(1L);
        categoriaFake.setNombre("Licores");

        productoFake = new ProductoEntity();
        productoFake.setId(1L);
        productoFake.setNombre("1/2Vodka");
        productoFake.setPrecio(45.500);
        productoFake.setStock(10);
        productoFake.setCategoria(categoriaFake);

    }

    @Test
    void crear_deberiaReturnearDTO() {
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("1/2Vodka");
        request.setPrecio(45.500);
        request.setStock(10);
        request.setCategoriaId(1L);

        when(categoriaRepository.findById(1l)).thenReturn(Optional.of(categoriaFake));
        when(productoRepository.save(any(ProductoEntity.class))).thenReturn(productoFake);

        ProductoResponseDTO resultado = productoService.crear(request);
        assertNotNull(resultado);
        assertEquals("1/2Vodka", resultado.getNombre());
        assertEquals("Licores", resultado.getCategoriaNombre());
    }


    @Test
    void crear_deberiaLanzarExcepcion_cuandoCategoriaNoExiste() {
        // Arrange
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setCategoriaId(99L);

        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productoService.crear(request));
    }

    @Test
    void obtenerPorId_deberiaRetornarDTO_cuandoExiste() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));

        ProductoResponseDTO resultado = productoService.obtenerPorId(1L);

        assertEquals("1/2Vodka", resultado.getNombre());
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.obtenerPorId(99L));
    }

    @Test
    void listar_deberiaRetornarListaDeDTO() {
        when(productoRepository.findAll()).thenReturn(List.of(productoFake));

        List<ProductoResponseDTO> resultado = productoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("1/2Vodka", resultado.get(0).getNombre());
    }

    @Test
    void actualizar_deberiaRetornarDTOActualizado() {
        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Agua Premium");
        request.setPrecio(2000.0);
        request.setStock(50);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoFake));
        when(productoRepository.save(any(ProductoEntity.class))).thenReturn(productoFake);

        ProductoResponseDTO resultado = productoService.actualizar(1L, request);

        assertNotNull(resultado);
        verify(productoRepository).save(any(ProductoEntity.class));
    }

    @Test
    void eliminar_deberiaEliminar_cuandoExiste() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.eliminar(1L);

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminar_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(productoRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> productoService.eliminar(99L));
    }
}
