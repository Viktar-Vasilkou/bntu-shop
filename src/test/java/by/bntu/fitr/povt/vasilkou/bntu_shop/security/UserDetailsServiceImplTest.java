package by.bntu.fitr.povt.vasilkou.bntu_shop.security;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.RegistrationDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.RoleRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Role;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserDetailsServiceImplTest {

    private static final String TEST_LOGIN = "Victor";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_ROLE_NAME = "ROLE_USER";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private static final List<MyUserDetails> usersDetails = List.of(
            new MyUserDetails(),
            new MyUserDetails()
    );


    @Test
    public void loadUserByUsername_tryToLoadFromDBVictor_userVictor() {
        usersDetails.get(0).setUser(new User());
        usersDetails.get(0).getUser().setLogin(TEST_LOGIN);

        UserDetails expected = usersDetails.get(0);
        when(userRepository.findByLogin(TEST_LOGIN)).thenReturn(Optional.of(usersDetails.get(0).getUser()));

        UserDetails actual = userDetailsService.loadUserByUsername(TEST_LOGIN);

        assertEquals(actual, expected);
        verify(userRepository, times(1)).findByLogin(TEST_LOGIN);
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_tryToThrowException_throwUsernameNotFoundException() {
        UserDetails expected = usersDetails.get(0);
        when(userRepository.findByLogin(TEST_LOGIN)).thenThrow(new UsernameNotFoundException("User not found"));

        UserDetails actual = userDetailsService.loadUserByUsername(TEST_LOGIN);

        assertEquals(actual, expected);
        verify(userRepository, times(1)).findByLogin(TEST_LOGIN);
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    public void saveNewUser_tryToSaveNewUser_true(){
        RegistrationDto user = new RegistrationDto();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        Role role = new Role();
        role.setRoleName(TEST_ROLE_NAME);

        when(userRepository.findByLogin(TEST_LOGIN)).thenReturn(Optional.empty());
        when(roleRepository.findByRoleName(TEST_ROLE_NAME)).thenReturn(role);

        boolean isSaved = userDetailsService.saveNewUser(user);

        assertTrue(isSaved);
        verify(userRepository, times(1)).findByLogin(TEST_LOGIN);
        verify(passwordEncoder, times(1)).encode(TEST_PASSWORD);
        verify(roleRepository, times(1)).findByRoleName(TEST_ROLE_NAME);
    }

    @Test
    public void saveNewUser_tryToSaveNewUser_false(){
        RegistrationDto user = new RegistrationDto();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        Role role = new Role();
        role.setRoleName(TEST_ROLE_NAME);

        when(userRepository.findByLogin(TEST_LOGIN)).thenReturn(Optional.of(new User()));
        when(roleRepository.findByRoleName(TEST_ROLE_NAME)).thenReturn(role);

        boolean isSaved = userDetailsService.saveNewUser(user);

        assertFalse(isSaved);
        verify(userRepository, times(1)).findByLogin(TEST_LOGIN);
        verify(passwordEncoder, times(0)).encode(TEST_PASSWORD);
        verify(roleRepository, times(0)).findByRoleName(TEST_ROLE_NAME);
        verifyNoMoreInteractions(userRepository, passwordEncoder, roleRepository);
    }

    @Test
    public void saveNewUser_tryToSaveNull_false(){
        RegistrationDto user = null;

        boolean actual = userDetailsService.saveNewUser(user);

        assertFalse(actual);
    }
}