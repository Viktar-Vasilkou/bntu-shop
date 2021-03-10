package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;

import java.util.List;

public interface UserService {
    User getById(Long id);

    User save(User user);

    void delete(User user);

    List<UserDto> getAll();
}
