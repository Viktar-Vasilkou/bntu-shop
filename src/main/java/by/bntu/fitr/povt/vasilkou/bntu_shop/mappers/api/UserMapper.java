package by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;

public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
