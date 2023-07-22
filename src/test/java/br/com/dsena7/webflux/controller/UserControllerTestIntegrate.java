package br.com.dsena7.webflux.controller;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.service.impl.UserServiceImpl;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class UserControllerTestIntegrate {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserServiceImpl service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient client;

    @Test
    @DisplayName("Test endpoint save successfully")
    void testSaveWithSuccess() {
        UserRequestDto requestDto = UserRequestDto.builder()
                .name("Diego")
                .email("diego.sena@gmail.com")
                .password("password")
                .build();

        UserEntity entity = UserEntity.builder()
                .id("7")
                .email("diego.sena@gmail.com")
                .name("Diego")
                .password("password")
                .build();

        when(service.saveUser(ArgumentMatchers.any(UserRequestDto.class))).thenReturn(Mono.just(entity));
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto)).exchange()
                .expectStatus().isCreated();
        Mockito.verify(service,times(1)).saveUser(any(UserRequestDto.class));
    }

    @Test
    @DisplayName("Test endpoint save successfully")
    void testSaveNotSuccess() {
        UserRequestDto requestDto = UserRequestDto.builder()
                .name("Diego ")
                .email("diego.sena@gmail.com")
                .password("password")
                .build();

        UserEntity entity = UserEntity.builder()
                .id("7")
                .email("diego.sena@gmail.com")
                .name("Diego")
                .password("password")
                .build();

        when(service.saveUser(ArgumentMatchers.any(UserRequestDto.class))).thenReturn(Mono.just(entity));
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto)).exchange()
                .expectStatus().isBadRequest();
    }
}
