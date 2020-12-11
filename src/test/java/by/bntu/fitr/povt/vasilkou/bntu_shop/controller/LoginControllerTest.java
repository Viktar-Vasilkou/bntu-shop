package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.RegistrationDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.security.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginControllerTest {

    private static final String TEST_SUCCESS_URL = "redirect:/";
    private static final String TEST_ERROR_URL = TEST_SUCCESS_URL + "registration?error";
    private static final RegistrationDto user = new RegistrationDto();

    @InjectMocks
    private LoginController controller;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void makeRegistration_tryToRegistration_success() {
        user.setLogin("login");
        user.setPassword("password");

        when(userDetailsService.saveNewUser(user)).thenReturn(true);

        String actual = controller.makeRegistration(user, bindingResult);

        assertEquals(actual, TEST_SUCCESS_URL);
        verify(userDetailsService, atLeastOnce()).saveNewUser(user);
    }

    @Test
    public void makeRegistration_tryToRegistrationEmpty_error() {
        user.setLogin("");
        user.setPassword("");

        when(userDetailsService.saveNewUser(user)).thenReturn(false);

        String actual = controller.makeRegistration(user, bindingResult);

        assertEquals(actual, TEST_ERROR_URL);
        verify(userDetailsService, never()).saveNewUser(user);
        verifyNoMoreInteractions(userDetailsService);
    }
}