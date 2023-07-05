package br.com.dsena7.webflux.service.impl;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.exceptions.ObjectNotFoundException;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.repository.UserRepository;
import br.com.dsena7.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Mono<UserEntity> getOneUserById(String id) {
        return userRepository.findOneUserById(id).switchIfEmpty(
                Mono.error(new ObjectNotFoundException(
                        format("Object not found, id: %s and type: %s", id, UserEntity.class.getSimpleName())))
        );
    }

    @Override
    public Mono<UserEntity> saveUser(UserRequestDto userRequestDto) {
        return userRepository.save(userMapper.toEntity(userRequestDto));
    }

    @Override
    public Flux<UserEntity> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
