package edu.miu.cs545.simplespringsecurity.services;

import edu.miu.cs545.simplespringsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmailAddress(String emailAddress);
    User save(User user);
    User update(Long id, User user);
    User delete(Long id);
    PasswordEncoder getPasswordEncoder();
}
