package br.com.dsena7.webflux.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidatonError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FielError> errors = new ArrayList<>();
    ValidatonError(LocalDateTime timestamp, String path, Integer status, String error, String message) {
        super(timestamp, path, status, error, message);
    }

    public void addErrorsList(String fieldName, String message){
        this.errors.add(new FielError(fieldName, message));
    }

    @Getter
    @AllArgsConstructor
    private static final class FielError{
        private String fieldName;
        private String message;
    }
}

