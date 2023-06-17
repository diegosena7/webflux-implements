package br.com.dsena7.webflux.model;

public record UserResponseDto(
        String id,
        String name,
        String email
) {
}
