package com.alura.forohub.domain.topico;

import java.time.LocalDateTime;
import java.time.ZoneId;

public record TopicoBodyResponse(
    Long id,
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    Boolean status,
    Long idAutor,
    String nombreCurso
) {
  public TopicoBodyResponse(Topico topico){
    this(topico.getId(), topico.getTitulo(), topico.getMensaje(), LocalDateTime.ofInstant(topico.getFechaCreacion(), ZoneId.systemDefault()),
        topico.getStatus(), topico.getAutor().getId(), topico.getCurso().getNombre());
  }
}