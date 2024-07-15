package com.alura.forohub.domain.topico.validaciones;

import com.alura.forohub.domain.topico.Topico;
import com.alura.forohub.domain.topico.TopicoBodyResquest;
import com.alura.forohub.domain.topico.TopicoRepository;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;

@Component
class RegistroNoDuplicado implements ValidadorRegistrarTopico{

  private TopicoRepository topicoRepository;

  public RegistroNoDuplicado(TopicoRepository topicoRepository) {
    this.topicoRepository = topicoRepository;
  }

  @Override
  public void validar(TopicoBodyResquest datos) {
    Topico topico = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
    if(topico != null) {
      throw new ValidationException("Este topico ya existe");
    }
  }
  
}