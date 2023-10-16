package edu.miu.cs545.simplespringsecurity.repositories;

import edu.miu.cs545.simplespringsecurity.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailIgnoreCase(String email);
}
