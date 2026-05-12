package com.cashpoint.back;

import com.cashpoint.bck.persistencia.dtos.CategoriaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.CategoriaResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.repositorios.CategoriaRepository;
import com.cashpoint.bck.servicios.Implementaxion.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;


    @Test
    void crear_RetornaDTO_DatosValidos() {

        CategoriaRequestDTO request = new CategoriaRequestDTO();
        request.setNombre("Licores");
        request.setDescripcion("bebidas alcoholicas");

        CategoriaEntity categoriaGuardada = new CategoriaEntity();
        categoriaGuardada.setId(1L);
        categoriaGuardada.setNombre("Licores");
        categoriaGuardada.setDescripcion("bebidas alcoholicas");

        when(categoriaRepository.existsByNombre("Licores")).thenReturn(false);
        when(categoriaRepository.save(any(CategoriaEntity.class))).thenReturn(categoriaGuardada);

        CategoriaResponseDTO resultado = categoriaService.crear(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Licores", resultado.getNombre());
        verify(categoriaRepository).save(any(CategoriaEntity.class));
    }

    @Test
    void crear_LanzaExcepcion_whenNombreRepetio() {
        CategoriaRequestDTO request = new CategoriaRequestDTO();
        request.setNombre("Licores");

        when(categoriaRepository.existsByNombre("Licores")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> categoriaService.crear(request));
        verify(categoriaRepository, never()).save(any());

    }

    @Test
    void listar_retornaListaDTOs() {

        CategoriaEntity c1 = new CategoriaEntity();
        c1.setId(1L);
        c1.setNombre("Licores");

        CategoriaEntity c2 = new CategoriaEntity();
        c2.setId(2L);
        c2.setNombre("Zigarros");

        when(categoriaRepository.findAll()).thenReturn(List.of(c1, c2));

        List<CategoriaResponseDTO> resultado = categoriaService.listar();

        assertEquals(2, resultado.size());
        assertEquals("Licores", resultado.get(0).getNombre());
        assertEquals("Zigarroz", resultado.get(1).getNombre());

    }

    @Test
    void eliminar_eliminaWhenIdExiste() {

        when(categoriaRepository.existsById(1L)).thenReturn(true);

        categoriaService.eliminar(1L);

        verify(categoriaRepository).deleteById(1L);
    }

    @Test
    void eliminar_lanzaExcepcionCuandoIdInexistente() {


        when(categoriaRepository.existsById(677L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> categoriaService.eliminar(677L));
        verify(categoriaRepository, never()).deleteById(any());
    }


}
