package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.api.UserMapper;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final List<User> users = List.of(
            User.builder().build(),
            User.builder().build()
    );

    private static final List<User> usersAll = List.of(
            User.builder().build(),
            User.builder().build()
    );

    @Test
    public void testGetById_tryToGetUserFromDB_user() {
        Long id = 1L;
        users.get(0).setId(id);

        User expected = users.get(0);
        when(userRepository.findById(id)).thenReturn(Optional.of(users.get(0)));

        User actual = userService.getById(id);

        assertEquals(expected, actual);

        verify(userRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetById_tryToGetUserFromDB_newUser() {
        Long id = 10L;

        User expected = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        User actual = userService.getById(id);

        assertEquals(actual, expected);
        verify(userRepository, times(1)).findById(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSave_tryToSaveUser_Success(){
        User expected = users.get(0);
        expected.setId(1L);

        when(userRepository.save(new User())).thenReturn(User.builder().id(1L).build());

        User actual = userService.save(new User());

        assertEquals(expected.getId(), actual.getId());

        verify(userRepository, times(1)).save(new User());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testDelete_tryToDeleteUser_Deleted(){
        userService.delete(User.builder().id(1L).build());
        verify(userRepository, times(1)).delete(User.builder().id(1L).build());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetAll_tryToGetAllUsersDto_usersDto(){

        when(userRepository.findAll()).thenReturn(usersAll);
        when(userMapper.toDto(User.builder().build())).thenReturn(UserDto.builder().build());

        List<UserDto> expected = List.of(UserDto.builder().build(), UserDto.builder().build());
        List<UserDto> actual = userService.getAll();

        assertEquals(expected, actual);
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(users.size())).toDto(User.builder().build());
    }
}