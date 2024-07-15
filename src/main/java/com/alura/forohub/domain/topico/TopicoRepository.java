package com.alura.forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

  Topico findByTituloAndMensaje(String titulo, String mensaje);
}