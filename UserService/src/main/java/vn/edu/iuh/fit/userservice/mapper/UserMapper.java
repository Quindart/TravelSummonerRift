package vn.edu.iuh.fit.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.edu.iuh.fit.userservice.dtos.requests.UserRegisterRequest;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", source = "role")
    @Mapping(target = "email", source = "email")
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", source = "role")
    @Mapping(target = "email", source = "email")
    User toUser(UserRegisterRequest request);
}
