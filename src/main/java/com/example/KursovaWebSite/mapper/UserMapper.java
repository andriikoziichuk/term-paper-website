package com.example.KursovaWebSite.mapper;

import com.example.KursovaWebSite.dtos.UserDTO;
import com.example.KursovaWebSite.models.user.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO dto);

    @InheritInverseConfiguration
    UserDTO fromUser(User user);

    List<User> toPeopleList(List<UserDTO> userDTOS);

    List<UserDTO> fromPeopleList(List<User> books);

}
