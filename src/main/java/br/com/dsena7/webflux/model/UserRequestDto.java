package br.com.dsena7.webflux.model;

import br.com.dsena7.webflux.validator.ValidateSpacesInRequestAttributes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @ValidateSpacesInRequestAttributes
        @NotBlank(message = "Name must not be nulor empty")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @Email(message = "Invalid email address")
        @NotBlank(message = "Email must not be nulor empty")
        String email,
        @NotBlank(message = "Password must not be nulor empty")
        @Size(min = 3, max = 50, message = "Password must be between 3 and 20 characters")
        String password
) {
}
