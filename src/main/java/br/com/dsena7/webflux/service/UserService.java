package br.com.dsena7.webflux.service;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.model.UserRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<UserEntity> getOneUserById(String id);
    public Mono<UserEntity> saveUser(UserRequestDto userRequestDto);
    public Flux<UserEntity> findAllUsers();
    public Mono<UserEntity> updateUser(UserRequestDto userRequestDto, String id);
    public Mono<UserEntity> deleteUser(String id);
}
