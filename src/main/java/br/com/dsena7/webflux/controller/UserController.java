package br.com.dsena7.webflux.controller;

import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {

    @PostMapping
    ResponseEntity<Mono<Void>> saveOneUser(@RequestBody UserRequestDto request);

    @GetMapping(value = "/{id}")
    ResponseEntity<Mono<UserResponseDto>> findOneUser(@PathVariable String id);

    @GetMapping
    ResponseEntity<Flux<UserResponseDto>> findAllUsers(@PathVariable String id);

    @PatchMapping
    ResponseEntity<Mono<UserResponseDto>> updateUser(@PathVariable String id, @RequestBody UserRequestDto userRequestDto);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Mono<Void>> deleteOneUser(@PathVariable String id);
}
