package br.com.dsena7.webflux.mapper;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.model.UserRequestDto;
import br.com.dsena7.webflux.model.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,nullValueCheckStrategy = ALWAYS)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequestDto requestDto, @MappingTarget UserEntity userEntity);

    UserResponseDto toDto(UserEntity entity);
}
