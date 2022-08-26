package com.example.mapper;

import com.example.model.User;
import com.example.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper
public interface UserMapper {
    @Mapping(source ="username", target ="username")
    @Mapping(source ="birthDate", target ="userDOB")
    @Mapping(source ="password", target ="userpass")
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    @Mapping(source ="username", target ="username")
    @Mapping(source ="userDOB", target ="birthDate")
    @Mapping(source ="userpass", target ="password")
    User toUser(UserDTO userDTO);


}
