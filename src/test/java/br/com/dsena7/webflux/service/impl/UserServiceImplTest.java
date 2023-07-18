package br.com.dsena7.webflux.service.impl;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
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
        StepVerifier.create(result).expectNextMatches(user -> user instanceof UserEntity).expectComplete().verify();
    }
}