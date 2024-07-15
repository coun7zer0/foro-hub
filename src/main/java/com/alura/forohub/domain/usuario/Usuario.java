package com.alura.forohub.domain.usuario;

import java.util.HashSet;
import java.util.Set;

import com.alura.forohub.domain.topico.Topico;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "nombre")
public class Usuario {
  @Id
  @GeneratedValue
  private Long id;

  private String nombre;

  private String email;

  private String password;

  @Setter(value = AccessLevel.NONE)
  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Topico> topicos = new HashSet<>();

  public void setTopicos(Set<Topico> topicos) {
    topicos.forEach(topico -> topico.setAutor(this));
    this.topicos = topicos;
  }

  public void addTopico(Topico topico) {
    topicos.add(topico);
    topico.setAutor(this);
  }

  public void removeTopico(Topico topico){
    topicos.remove(topico);
    topico.setAutor(null);
  }
}