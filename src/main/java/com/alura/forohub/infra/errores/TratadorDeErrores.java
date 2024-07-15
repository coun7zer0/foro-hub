package com.alura.forohub.infra.errores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class TratadorDeErrores {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<String> tratarError404(EntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException e){
    List<DatosErrorValidacion> errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
    return ResponseEntity.badRequest().body(errores);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> errorHandlerValidacionesDeNegocio(Exception e){
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  private record DatosErrorValidacion(String campo, String error){
    public DatosErrorValidacion(FieldError error) {
      this(error.getField(), error.getDefaultMessage());
    }
  }
}