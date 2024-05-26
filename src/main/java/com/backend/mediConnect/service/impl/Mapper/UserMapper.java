package com.backend.mediConnect.service.impl.Mapper;

import com.backend.mediConnect.dto.UserDto;
import com.backend.mediConnect.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.roleName", target = "role")
    public UserDto toUserDto(User user);
    @Mapping(target = "authorities", ignore = true)
    public User toUser(UserDto userDto);
}
