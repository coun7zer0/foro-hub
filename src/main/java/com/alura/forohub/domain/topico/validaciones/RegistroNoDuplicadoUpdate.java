package com.alura.forohub.domain.topico.validaciones;

import com.alura.forohub.domain.topico.Topico;
import com.alura.forohub.domain.topico.TopicoBodyUpdate;
import com.alura.forohub.domain.topico.TopicoRepository;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;

@Component
class RegistroNoDuplicadoUpdate implements ValidadorActualizarTopico{

  private TopicoRepository topicoRepository;

  public RegistroNoDuplicadoUpdate(TopicoRepository topicoRepository) {
    this.topicoRepository = topicoRepository;
  }

  @Override
  public void validar(TopicoBodyUpdate datos) {
    if (datos.titulo() == null || datos.mensaje() == null) {
      return;
    }

    Topico topico = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
    if(topico != null) {
      throw new ValidationException("Este topico ya existe");
    }
  }

}