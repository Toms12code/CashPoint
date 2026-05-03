package com.cashpoint.bck.persistencia.repositorios;

import com.cashpoint.bck.persistencia.entidades.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    boolean existsByNombre(String nombre);
}
