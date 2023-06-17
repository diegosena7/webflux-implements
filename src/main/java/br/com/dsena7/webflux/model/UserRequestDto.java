package br.com.dsena7.webflux.dtos;

public record UserRequestDto(
        String name,
        String email,
        String password
) {
}
