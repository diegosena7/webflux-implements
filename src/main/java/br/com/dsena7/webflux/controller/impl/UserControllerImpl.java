package br.com.dsena7.webflux.controller.impl;

import br.com.dsena7.webflux.controller.UserController;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
import br.com.dsena7.webflux.service.UserService;
import br.com.dsena7.webflux.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;
    @Override
    public ResponseEntity<Mono<Void>> saveOneUser(UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(request).then());
    }

    @Override
    public ResponseEntity<Mono<UserResponseDto>> findOneUser(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Flux<UserResponseDto>> findAllUsers(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Mono<UserResponseDto>> updateUser(String id, UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Mono<Void>> deleteOneUser(String id) {
        return null;
    }
}
