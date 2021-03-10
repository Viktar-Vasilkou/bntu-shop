package by.bntu.fitr.povt.vasilkou.bntu_shop.security;


import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.RegistrationDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.RoleRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new MyUserDetails(userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user")));
    }

    @Transactional
    public boolean saveNewUser(RegistrationDto registrationUser){

        if (registrationUser == null) {
            return false;
        }

        if (userRepository.findByLogin(registrationUser.getLogin()).isEmpty()){
            User user = User.builder()
                    .login(registrationUser.getLogin())
                    .password(passwordEncoder.encode(registrationUser.getPassword()))
                    .roles(Collections.singleton(roleRepository.findByRoleName("ROLE_USER")))
                    .active(true)
                    .build();

            userRepository.save(user);
            return true;
        }

        return false;
    }
}
