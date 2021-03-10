package by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperImplTest {

    private static final User TEST_USER;

    private static final UserDto TEST_USER_DTO;

    static {
        TEST_USER = User.builder()
                .id(1L)
                .name("Test")
                .surname("Test")
                .phoneNumber("test")
                .login("test")
                .build();

        TEST_USER_DTO = UserDto.builder()
                .id(1L)
                .name("Test")
                .surname("Test")
                .phoneNumber("test")
                .login("test")
                .build();
    }

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    public void testToDto_mapToDto_null() {
        assertNull(userMapper.toDto(null));
    }

    @Test
    public void testToDto_mapToDto_UserDto() {
        UserDto expected = UserDto.builder()
                .id(1L)
                .name("Test")
                .surname("Test")
                .phoneNumber("test")
                .login("test")
                .build();
        UserDto actual = userMapper.toDto(TEST_USER);

        assertEquals(expected, actual);
    }

    @Test
    public void testToEntity_mapToUser_null() {
        assertNull(userMapper.toEntity(null));
    }

    @Test
    public void testToEntity_mapToUserNotExists_User() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        User expected = User.builder()
                .name("Test")
                .surname("Test")
                .phoneNumber("test")
                .login("test")
                .build();

        User actual = userMapper.toEntity(TEST_USER_DTO);

        assertEquals(expected, actual);

        verify(userRepository, times(1)).existsById(id);
    }

    @Test
    public void testToEntity_mapToUserExists_User() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.getOne(id)).thenReturn(TEST_USER);

        User expected = User.builder()
                .id(id)
                .name("Test")
                .surname("Test")
                .phoneNumber("test")
                .login("test")
                .build();

        User actual = userMapper.toEntity(TEST_USER_DTO);

        assertEquals(expected, actual);

        verify(userRepository, times(1)).existsById(id);
    }
}