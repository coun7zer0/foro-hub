package com.alura.forohub.domain.topico;

import java.time.Instant;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.usuario.Usuario;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"titulo", "mensaje", "fechaCreacion", "autor", "curso"})
public class Topico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  private String mensaje;

  @CreationTimestamp
  private Instant fechaCreacion = Instant.now();

  private Boolean status = true;

  // probar cuando termine el @CreatedBy
  @ManyToOne(fetch = FetchType.LAZY)
  private Usuario autor;

  @ManyToOne(fetch = FetchType.LAZY)
  private Curso curso;

  public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
    this.titulo = titulo;
    this.mensaje = mensaje;
    this.autor = autor;
    this.curso = curso;
  }
}