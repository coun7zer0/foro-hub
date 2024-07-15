package com.alura.forohub.controller;

import java.net.URI;

import com.alura.forohub.domain.topico.TopicoBodyResponse;
import com.alura.forohub.domain.topico.TopicoBodyResquest;
import com.alura.forohub.domain.topico.TopicoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

  private TopicoService topicoService;

  public TopicoController(TopicoService topicoService) {
    this.topicoService = topicoService;
  }

  @PostMapping
  public ResponseEntity<TopicoBodyResponse> registrar(
      @Valid @RequestBody TopicoBodyResquest datosTopico,
      UriComponentsBuilder uriComponentsBuilder) {

    TopicoBodyResponse datosResponse = topicoService.registrar(datosTopico);
    URI uri = uriComponentsBuilder
      .path("/medicos/{id}")
      .buildAndExpand(datosResponse.id())
      .toUri();

    return ResponseEntity.created(uri).body(datosResponse);
  }

  @GetMapping
  public ResponseEntity<Page<TopicoBodyResponse>> listar(Pageable pageable) {
    return ResponseEntity.ok(topicoService.listar(pageable));
  }
}