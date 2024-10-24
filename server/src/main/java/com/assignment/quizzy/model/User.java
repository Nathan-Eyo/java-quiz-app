package com.assignment.quizzy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        System.out.println(UserDetails.super.isAccountNonExpired());
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        System.out.println(UserDetails.super.isAccountNonLocked());
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        System.out.println(UserDetails.super.isCredentialsNonExpired());
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println( UserDetails.super.isEnabled());
        return true;
    }
}
