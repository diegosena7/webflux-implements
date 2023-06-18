package br.com.dsena7.webflux.exceptions;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private String path;
    private Integer status;
    private String error;
    private String message;
}
