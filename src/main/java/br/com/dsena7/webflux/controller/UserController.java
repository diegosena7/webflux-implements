package br.com.dsena7.webflux.controller;

import br.com.dsena7.webflux.mapper.UserMapper;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
import br.com.dsena7.webflux.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController{

    private final UserServiceImpl userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<Mono<Void>> saveOneUser(@RequestBody @Valid UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(request).then());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<UserResponseDto>> findOneUserById(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.getOneUserById(id).map(object -> mapper.toDto(object)));
    }

    @GetMapping
    public ResponseEntity<Flux<UserResponseDto>> findAllUsers() {
        return ResponseEntity.ok().body(userService.findAllUsers()
                .map(users -> mapper.toDto(users)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mono<UserResponseDto>> updateUser(@PathVariable String id, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok().body(userService.updateUser(userRequestDto, id).map(users -> mapper.toDto(users)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteOneUser(@PathVariable String id) {
        // Implemente a lógica para excluir um usuário pelo ID e retornar a resposta adequada
        return null;
    }
}
