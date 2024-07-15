package com.alura.forohub.domain.topico;

import java.util.List;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.curso.CursoRepository;
import com.alura.forohub.domain.topico.validaciones.ValidadorActualizarTopico;
import com.alura.forohub.domain.topico.validaciones.ValidadorRegistrarTopico;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.domain.usuario.UsuarioRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TopicoService {

  private TopicoRepository topicoRepository;
  private UsuarioRepository usuarioRepository;
  private CursoRepository cursoRepository;

  private List<ValidadorRegistrarTopico> validadorRegistrarTopico;
  private List<ValidadorActualizarTopico> validadorActualizarTopicos;

  public TopicoService(
      TopicoRepository topicoRepository, UsuarioRepository usuarioRepository,
      CursoRepository cursoRepository, List<ValidadorRegistrarTopico> validadorRegistrarTopico,
      List<ValidadorActualizarTopico> validadorActualizarTopicos) {
    this.topicoRepository = topicoRepository;
    this.usuarioRepository = usuarioRepository;
    this.cursoRepository = cursoRepository;
    this.validadorRegistrarTopico = validadorRegistrarTopico;
    this.validadorActualizarTopicos = validadorActualizarTopicos;
  }

  public TopicoBodyResponse registrar(TopicoBodyResquest topicoData) {
    Usuario usuarioExistente = usuarioRepository.findById(topicoData.idAutor())
      .orElseThrow(() -> new EntityNotFoundException("El usuario con el id '" + topicoData.idAutor() + "' no existe"));

    Curso cursoExistente = cursoRepository.findByNombre(topicoData.nombreCurso())
      .orElseThrow(() -> new EntityNotFoundException("El curso con el nombre '" + topicoData.nombreCurso() + "' no existe"));

    validadorRegistrarTopico.forEach(v -> v.validar(topicoData));

    Topico nuevoTopico = new Topico(topicoData.titulo(), topicoData.mensaje(), usuarioExistente, cursoExistente);
    Topico topicoGuardado = topicoRepository.save(nuevoTopico);

    return new TopicoBodyResponse(topicoGuardado);
  }

  public Page<TopicoBodyResponse> listar(Pageable pageable) {
    return topicoRepository.findAll(pageable).map(topico -> new TopicoBodyResponse(topico));
  }

  public TopicoBodyResponse obtener(Long id) {
    return topicoRepository.findById(id)
      .map(TopicoBodyResponse::new)
      .orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public void actualizar(TopicoBodyUpdate datos, Long id) {
    validadorActualizarTopicos.forEach(v -> v.validar(datos));

    Topico existingTopico = topicoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    existingTopico.ActualizarTopico(datos);
  }

  public void eliminar(Long id) {
    Topico existingTopico = topicoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    topicoRepository.deleteById(existingTopico.getId());
  }
}
