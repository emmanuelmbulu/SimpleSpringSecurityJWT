package edu.miu.cs545.simplespringsecurity.services;

import edu.miu.cs545.simplespringsecurity.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    Role getRoleById(Long id);
    List<Role> getAllRoles();

}
