package br.com.dsena7.webflux.mapper;

import br.com.dsena7.webflux.entity.UserEntity;
import br.com.dsena7.webflux.model.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserRequestDto requestDto);
}
