package com.alura.forohub.domain.curso;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
  Optional<Curso> findByNombre(String nombre);
}