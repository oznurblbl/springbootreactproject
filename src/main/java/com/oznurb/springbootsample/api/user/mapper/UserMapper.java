package com.oznurb.springbootsample.api.user.mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oznurb.springbootsample.api.user.dto.UserDto;
import com.oznurb.springbootsample.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

//	@Mapping(target = "id", source = "userId")
	User dtoToEntity(UserDto dto);
	
	UserDto entityToDto(User entity);
	
	List<UserDto> entityListToDtoList(List<User> entityList);
	
	List<User> dtoListToEntityList(List<UserDto> dtoList);
	
}
