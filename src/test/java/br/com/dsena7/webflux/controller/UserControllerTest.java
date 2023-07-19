package br.com.dsena7.webflux.controller;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
import br.com.dsena7.webflux.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserServiceImpl service;

    @InjectMocks UserController controller;

    @Mock
    private UserMapper userMapper;

    @Test
    void saveUser(){
        UserRequestDto requestDto = UserRequestDto.builder()
                .email("diego.sena@gmail.com")
                .name("Diego S Sena")
                .password("password")
                .build();

        when(service.saveUser(any(UserRequestDto.class))).thenReturn(Mono.empty());
        ResponseEntity<Mono<Void>> responseEntity = controller.saveOneUser(requestDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void getById(){
        UserEntity entity = UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build();

        when(service.getOneUserById(anyString())).thenReturn(Mono.just(entity));

        ResponseEntity<Mono<UserResponseDto>> responseEntity = controller.findOneUserById("123");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void findAllUsers(){
        UserEntity entity = UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build();

        when(service.findAllUsers()).thenReturn(Flux.just(entity));

        ResponseEntity<Flux<UserResponseDto>> responseEntity = controller.findAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateUser(){
        UserEntity entity = UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build();

        UserRequestDto requestDto = UserRequestDto.builder()
                .email("diego.sena@gmail.com")
                .name("Diego S Sena")
                .password("password")
                .build();

        UserResponseDto responseDto = UserResponseDto.builder()
                .id("7")
                .name("Diego")
                .email("teste@gmail.com")
                .build();

        when(service.updateUser(requestDto, "123")).thenReturn(Mono.just(entity));

        ResponseEntity<Mono<UserResponseDto>> responseEntity = controller.updateUser("123", requestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteUser(){
        UserEntity entity = UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build();

        when(service.deleteUser("123")).thenReturn(Mono.just(entity));

        ResponseEntity<Mono<Void>> responseEntity = controller.deleteOneUser("123");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
