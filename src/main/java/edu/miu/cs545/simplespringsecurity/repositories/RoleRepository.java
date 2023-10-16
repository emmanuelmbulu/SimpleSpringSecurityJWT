package edu.miu.cs545.simplespringsecurity.repositories;

import edu.miu.cs545.simplespringsecurity.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
