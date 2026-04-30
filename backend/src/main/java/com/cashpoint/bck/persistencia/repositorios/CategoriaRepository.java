package com.cashpoint.back.persistencia.repositorios;

import com.cashpoint.back.persistencia.entidades.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    boolean existsByNombre(String nombre);
}
