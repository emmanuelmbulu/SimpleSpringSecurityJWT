package edu.miu.cs545.simplespringsecurity.services.implementations;

import edu.miu.cs545.simplespringsecurity.model.Role;
import edu.miu.cs545.simplespringsecurity.model.User;
import edu.miu.cs545.simplespringsecurity.repositories.RoleRepository;
import edu.miu.cs545.simplespringsecurity.repositories.UserRepository;
import edu.miu.cs545.simplespringsecurity.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImplementation implements UserService {
    final private UserRepository _repository;
    final private RoleRepository _roleRepository;
    private PasswordEncoder _passwordEncoder;

    public UserServiceImplementation(UserRepository repository, RoleRepository roleRepository) {
        this._repository = repository;
        this._roleRepository = roleRepository;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        if(this._passwordEncoder == null) this._passwordEncoder = new BCryptPasswordEncoder();
        return this._passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Iterable<User> data = _repository.findAll();
        for(User user: data) users.add(user);
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return _repository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmailAddress(String emailAddress) {
        return _repository.findByEmailIgnoreCase(emailAddress).orElse(null);
    }

    @Override
    public User save(User user) {
        String password = user.getPassword();
        user.setPassword(_passwordEncoder.encode(password));
        if(user.getAuthorities() == null) {
            user.addRole(getDefaultUserRole());
        }
        return _repository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        if(user == null) return null;

        User userToUpdate = this.getUserById(id);
        if(userToUpdate == null) return null;

        userToUpdate.setEmail(user.getEmail());

        String password = user.getPassword();
        if(password != null && !password.isEmpty()) {
            userToUpdate.setPassword(_passwordEncoder.encode(password));
        }

        return _repository.save(user);
    }

    @Override
    public User delete(Long id) {
        User user = this.getUserById(id);
        if(user == null) return null;

        _repository.delete(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.isEmpty()) throw new UsernameNotFoundException("Please provide a valid username");
        User user = _repository.findByUsername(username).orElse(null);

        if(user == null) throw new UsernameNotFoundException("We could not find any user with the username: " + username);
        return user;
    }

    private Role getDefaultUserRole() {
        return _roleRepository.findById(2L).orElse(null);
    }
}
