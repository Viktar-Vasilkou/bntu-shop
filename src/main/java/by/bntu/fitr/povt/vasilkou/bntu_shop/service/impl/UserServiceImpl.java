package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.api.UserMapper;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    public UserDto getByIdDto(Long id) {
        return userMapper.toDto(getById(id));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User edit(UserDto userDto) {
         return userRepository.save(userMapper.toEntity(userDto));
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
