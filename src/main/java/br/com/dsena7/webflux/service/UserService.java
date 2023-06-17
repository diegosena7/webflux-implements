package br.com.dsena7.webflux.service;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.model.UserRequestDto;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<UserEntity> getOneUserById(String id);
    public Mono<UserEntity> saveUser(UserRequestDto userRequestDto);
}
