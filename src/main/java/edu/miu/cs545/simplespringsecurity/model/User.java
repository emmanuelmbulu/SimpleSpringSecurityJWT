package edu.miu.cs545.simplespringsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@EqualsAndHashCode(exclude = "authorities")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = CascadeType.MERGE)
    private Set<Role> authorities;

    public void addRole(Role role) {
        if(authorities == null) authorities = new HashSet<>();
        authorities.add(role);
        role.addUser(this);
    }

    {
        accountNonLocked = true;
        accountNonExpired = true;
        credentialsNonExpired = true;
        enabled = true;
    }
}
