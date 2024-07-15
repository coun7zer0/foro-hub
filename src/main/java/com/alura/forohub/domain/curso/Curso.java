package com.alura.forohub.domain.curso;

import java.util.HashSet;
import java.util.Set;

import com.alura.forohub.domain.topico.Topico;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"nombre"})
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  private String categoria;

  @Setter(value = AccessLevel.NONE)
  @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Topico> topicos = new HashSet<>();

  public void setTopicos(Set<Topico> topicos) {
    topicos.forEach(topico -> topico.setCurso(this));
    this.topicos = topicos;
  }

  public void addTopico(Topico topico) {
    topicos.add(topico);
    topico.setCurso(this);
  }

  public void removeTopicos(Topico topico) {
    topicos.remove(topico);
    topico.setCurso(null);
  }
}