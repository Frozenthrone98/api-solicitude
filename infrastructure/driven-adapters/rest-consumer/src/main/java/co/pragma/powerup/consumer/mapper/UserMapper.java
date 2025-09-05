package co.pragma.powerup.consumer.mapper;

import co.pragma.powerup.consumer.authentication.UserRequest;
import co.pragma.powerup.rest.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRequest toRequest(User user);
}
