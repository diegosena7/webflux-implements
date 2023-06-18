package br.com.dsena7.webflux.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

    private String verifyDuplicateKeys(String message){
        if (message.contains("email dup key")){
            return "Email already exists";
        }
        return "Dup key exception";
    }
}
