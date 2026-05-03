package com.cashpoint.bck.persistencia.repositorios;

import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import com.cashpoint.bck.persistencia.entidades.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    List<ProductoEntity> findByCategoriaId(Long categoriaId);

    boolean existsByNombre(String nombre);
}
