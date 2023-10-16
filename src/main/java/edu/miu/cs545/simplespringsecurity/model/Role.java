package edu.miu.cs545.simplespringsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
public class Role implements GrantedAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @Nullable @Column(columnDefinition = "text")
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE)
    Set<User> users = new HashSet<>();

    public void addUser(User user) {
        if(users == null) users = new HashSet<>();
        users.add(user);
    }

    @Override
    public String toString() {
        return authority;
    }
}
