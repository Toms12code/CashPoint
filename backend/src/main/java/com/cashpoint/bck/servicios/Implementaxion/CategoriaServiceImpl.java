package com.cashpoint.bck.servicios.Implementaxion;

import com.cashpoint.bck.excepcione.NegocioException;
import com.cashpoint.bck.excepcione.NoHayException;
import com.cashpoint.bck.persistencia.dtos.CategoriaRequestDTO;
import com.cashpoint.bck.persistencia.dtos.CategoriaResponseDTO;
import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.repositorios.CategoriaRepository;
import com.cashpoint.bck.servicios.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO request) {
        if (categoriaRepository.existsByNombre(request.getNombre())) {
            throw new NegocioException("La categoria ya existe :(");
        }
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getNombre());

        CategoriaEntity guardada = categoriaRepository.save(categoria);

        CategoriaResponseDTO response = new CategoriaResponseDTO();
        response.setId(guardada.getId());
        response.setNombre(guardada.getNombre());
        response.setDescripcion(guardada.getDescripcion());
        return response;
    }

    @Override
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(c ->{
                    CategoriaResponseDTO dto = new CategoriaResponseDTO();
                    dto.setId(c.getId());
                    dto.setNombre(c.getNombre());
                    dto.setDescripcion(c.getDescripcion());
                    return dto;
                })
                .toList();
    }
    @Override
    public void eliminar(Long id) {
        if(!categoriaRepository.existsById(id)){
            throw new NoHayException("La categoria no existe :(");
        }
        categoriaRepository.deleteById(id);
    }
}
