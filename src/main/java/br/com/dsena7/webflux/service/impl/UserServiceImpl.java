package br.com.dsena7.webflux.service.impl;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.repository.UserRepository;
import br.com.dsena7.webflux.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Mono<UserEntity> getOneUserById(String id) {
        return userRepository.findOneUserById(id);
    }

    @Override
    public Mono<UserEntity> saveUser(UserRequestDto userRequestDto) {
        return userRepository.save(userMapper.toEntity(userRequestDto));
    }
}
