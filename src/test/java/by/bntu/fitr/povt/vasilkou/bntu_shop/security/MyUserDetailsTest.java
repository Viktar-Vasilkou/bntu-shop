package by.bntu.fitr.povt.vasilkou.bntu_shop.security;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Role;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsTest {

    private static final String TEST_ROLE_NAME = "ROLE_USER";
    private static final Set<Role> TEST_ROLES = new HashSet<>();

    @InjectMocks
    private MyUserDetails myUserDetails;

    @Mock
    private User user;

    @Before
    public void setRoles() {
        Role role1 = new Role();
        role1.setRoleName(TEST_ROLE_NAME);
        TEST_ROLES.add(role1);
    }

    @Test
    public void testGetAuthorities_tryToGetAuthorities_hasUserAuthority() {
        myUserDetails.getUser().setRoles(TEST_ROLES);

        List<SimpleGrantedAuthority> expected = TEST_ROLES.stream()
                .map(p -> new SimpleGrantedAuthority(p.getRoleName())).collect(Collectors.toList());

        when(user.getRoles()).thenReturn(TEST_ROLES);

        Collection<? extends GrantedAuthority> actual = myUserDetails.getAuthorities();

        assertEquals(actual, expected);

        verify(user, atLeastOnce()).getRoles();
    }

    @Test
    public void testGetAuthorities_tryToGetAuthorities_hasNoAuthority() {
        List<SimpleGrantedAuthority> expected = new ArrayList<>();

        when(user.getRoles()).thenReturn(new HashSet<>());

        Collection<? extends GrantedAuthority> actual = myUserDetails.getAuthorities();

        assertEquals(actual, expected);
        verify(user, atLeastOnce()).getRoles();
    }

    @Test
    public void testGetPassword_tryToGetPassword_Password(){
        String password = "password";
        when(user.getPassword()).thenReturn(password);

        assertEquals(password, myUserDetails.getPassword());
    }

    @Test
    public void testGetUsername_tryToGetUsername_Username(){
        String username = "login";
        when(user.getLogin()).thenReturn(username);

        assertEquals(username, myUserDetails.getUsername());
    }

    @Test
    public void testIsAccountNonExpired_true_true() {
        assertTrue(myUserDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked_true_true() {
        assertTrue(myUserDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired_true_true() {
       assertTrue(myUserDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled_getUserStatus_True() {
        when(user.isActive()).thenReturn(true);

        assertTrue(myUserDetails.isEnabled());
    }
}