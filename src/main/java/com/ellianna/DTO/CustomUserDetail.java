package com.ellianna.DTO;
import com.ellianna.model.USER_ROLE;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


@Setter
@Getter
public class CustomUserDetail implements UserDetails {
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    @Getter
    private Long userId;
    public CustomUserDetail(String username, String password, USER_ROLE role, Long userId) {
        this.username = username;
        this.password = password;
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.toString());
        Set<GrantedAuthority> lst = new HashSet<>();
        lst.add(authority);
        this.authorities = lst;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String[] getRolesAsArray() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

    @Override
    public String toString() {
        return "CustomUserDetail{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", userId=" + userId +
                '}';
    }
}
