package com.alura.forohub.controller;

import com.alura.forohub.domain.usuario.LoginRequest;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.infra.security.DatosJWTToken;
import com.alura.forohub.infra.security.TokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private TokenService tokenService;

  public AuthenticationController(
      AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }


  @PostMapping
  public ResponseEntity<DatosJWTToken> login(@RequestBody @Valid LoginRequest loginRequest) {
    Authentication authenticationRequest =
        UsernamePasswordAuthenticationToken.unauthenticated(
            loginRequest.email(), loginRequest.password());

    Authentication authenticationResoponse =
        this.authenticationManager.authenticate(authenticationRequest);

    var JWTtoken = tokenService.generarToken((Usuario) authenticationResoponse.getPrincipal());
    return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
  }
}