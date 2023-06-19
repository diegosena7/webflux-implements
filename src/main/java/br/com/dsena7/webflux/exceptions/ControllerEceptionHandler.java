package br.com.dsena7.webflux.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static java.time.LocalTime.now;

@ControllerAdvice
public class ControllerEceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ResponseEntity<Mono<StandardError>> duplicateKeyException(DuplicateKeyException exception, ServerHttpRequest request) {
        return ResponseEntity.badRequest().body(Mono.just(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(verifyDuplicateKeys(exception.getMessage()))
                        .build()
        ));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ValidatonError>> validationError(WebExchangeBindException exception, ServerHttpRequest request){
        ValidatonError error = new ValidatonError(
                LocalDateTime.now(), request.getPath().toString(), HttpStatus.BAD_REQUEST.value(), "Validation error", "Error on validation attributes"
        );
        for(FieldError erros: exception.getBindingResult().getFieldErrors()){
            error.addErrorsList(erros.getField(), erros.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(Mono.just(error));
    }

    private String verifyDuplicateKeys(String message){
        if (message.contains("email dup key")){
            return "Email already exists";
        }
        return "Dup key exception";
    }
}
