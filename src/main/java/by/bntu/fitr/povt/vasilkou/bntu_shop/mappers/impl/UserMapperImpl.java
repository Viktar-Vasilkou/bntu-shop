package by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.api.UserMapper;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .surname(user.getSurname())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        if (userRepository.existsById(userDto.getId())) {
            user = userRepository.getOne(userDto.getId());
        }

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setLogin(userDto.getLogin());
        user.setPhoneNumber(userDto.getPhoneNumber());

        return user;
    }
}
