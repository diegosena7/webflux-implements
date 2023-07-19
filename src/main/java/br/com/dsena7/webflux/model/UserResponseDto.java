package br.com.dsena7.webflux.model;

import lombok.Builder;

@Builder
public record UserResponseDto(
        String id,
        String name,
        String email
) {
}
