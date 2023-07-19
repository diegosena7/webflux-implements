package br.com.dsena7.webflux.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerEceptionHandlerTest {


    @Mock
    private WebExchangeBindException bindException;

    @Mock
    private ObjectNotFoundException objectNotFoundException;

    @Mock
    private DuplicateKeyException duplicateKeyException;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock private RequestPath requestPath;

    @InjectMocks
    private ControllerEceptionHandler exceptionHandler;

    @Test
    public void testDuplicateKeyException() {
        when(duplicateKeyException.getMessage()).thenReturn("email dup key");
        ResponseEntity<Mono<StandardError>> responseEntity = exceptionHandler.duplicateKeyException(duplicateKeyException, serverHttpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testObjectNotFound() {
        when(objectNotFoundException.getMessage()).thenReturn("Object not found");
        when(serverHttpRequest.getPath()).thenReturn(requestPath);
        ResponseEntity<Mono<StandardError>> responseEntity = exceptionHandler.objectNotFound(objectNotFoundException, serverHttpRequest);
        assertEquals(HttpStatusCode.valueOf(404), responseEntity.getStatusCode());
    }
}
