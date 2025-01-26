package vn.edu.iuh.fit.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.iuh.fit.userservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.userservice.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", source = "role")
    UserResponse toUserResponse(User user);
}
