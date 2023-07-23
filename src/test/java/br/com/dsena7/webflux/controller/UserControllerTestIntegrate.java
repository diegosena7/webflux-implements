package br.com.dsena7.webflux.controller;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.exceptions.ObjectNotFoundException;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @DisplayName("Test endpoint save with not successfully")
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
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("Invalid name, field cannot have blank spaces at the beginning or end of the name");
    }

    @Test
    @DisplayName("Test endpoint findById with successfully")
    void testgetByIdWithSuccess() {

        final String id = "7";

        UserEntity entity = UserEntity.builder()
                .id("7")
                .email("diego.sena@gmail.com")
                .name("Diego")
                .password("password")
                .build();

        UserResponseDto response = UserResponseDto.builder()
                .id("7")
                .name("Diego")
                .email("diego.sena@gmail.com")
                .build();

        when(service.getOneUserById(ArgumentMatchers.anyString())).thenReturn(Mono.just(entity));
        when(mapper.toDto(any(UserEntity.class))).thenReturn(response);

        webTestClient.get().uri("/users/" + id).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.id").isEqualTo(response.id())
                .jsonPath("$.name").isEqualTo(response.name())
                .jsonPath("$.email").isEqualTo(response.email());
    }

    @Test
    public void testFindOneUserById_UserNotFound() {
        String userId = "nonExistingUser";
        when(service.getOneUserById(userId)).thenReturn(Mono.error(new ObjectNotFoundException("User with ID 'nonExistingUser' not found.")));

        webTestClient.get()
                .uri("/users/{id}", userId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value())
                .jsonPath("$.error").isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase())
                .jsonPath("$.message").isEqualTo("User with ID 'nonExistingUser' not found.")
                .jsonPath("$.timestamp").isNotEmpty()
                .jsonPath("$.path").isEqualTo("/users/" + userId);
    }
}
