package br.com.dsena7.webflux.service.impl;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void saveUser() {
        UserRequestDto requestDto = UserRequestDto.builder()
                .email("diego.sena@gmail.com")
                .name("Diego S Sena")
                .password("password")
                .build();

        UserEntity entity = UserEntity.builder()
                .id("3111111111121")
                .email("diego.sena@gmail.com")
                .name("Diego S Sena")
                .password("password")
                .build();

        when(userMapper.toEntity(any(UserRequestDto.class))).thenReturn(entity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(entity));

        Mono<UserEntity> result = service.saveUser(requestDto);
        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == UserEntity.class).expectComplete().verify();
    }

    @Test
    void findById(){
        when(userRepository.findOneUserById(anyString())).thenReturn(Mono.just(UserEntity.builder()
                        .password("password")
                        .name("Diego")
                        .id("7")
                        .email("diego.sena@gmail.com")
                .build()));
        Mono<UserEntity> entityMono = service.getOneUserById("123");
        StepVerifier.create(entityMono).expectNextMatches(user -> user.getClass() == UserEntity.class
        && user.getName().equals("Diego") && user.getId().equals("7")
        ).expectComplete().verify();
    }

    @Test
    void findAllUsers(){
        when(userRepository.findAllUsers()).thenReturn(Flux.just(UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build()));

        Flux<UserEntity> entity = service.findAllUsers();
        StepVerifier.create(entity).expectNextMatches(user -> user.getClass() == UserEntity.class
                && user.getName().equals("Diego") && user.getId().equals("7")
        ).expectComplete().verify();
    }

    @Test
    void updateUser(){
        UserRequestDto requestDto = UserRequestDto.builder()
                .email("diego.sena@gmail.com")
                .name("Diego")
                .password("password")
                .build();

        UserEntity entity = UserEntity.builder()
                .id("7")
                .email("diego.sena@gmail.com")
                .name("Diego")
                .password("password")
                .build();

        when(userMapper.toEntity(any(requestDto.getClass()), any(UserEntity.class))).thenReturn(entity);
        when(userRepository.findOneUserById(anyString())).thenReturn(Mono.just(entity));
        when(userRepository.save(entity)).thenReturn(Mono.just(entity));

        Mono<UserEntity> result = service.updateUser(requestDto, "123");
        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == UserEntity.class
                && user.getName().equals("Diego") && user.getId().equals("7")
        ).expectComplete().verify();

        Mockito.verify(userRepository, times(1)).save(entity);
    }

    @Test
    void deleteUser(){
        when(userRepository.findAndRemove(anyString())).thenReturn(Mono.just(UserEntity.builder()
                .password("password")
                .name("Diego")
                .id("7")
                .email("diego.sena@gmail.com")
                .build()));
        Mono<UserEntity> entityMono = service.deleteUser("123");
        StepVerifier.create(entityMono).expectNextMatches(user -> user.getClass() == UserEntity.class
                && user.getName().equals("Diego") && user.getId().equals("7")
        ).expectComplete().verify();
    }
}