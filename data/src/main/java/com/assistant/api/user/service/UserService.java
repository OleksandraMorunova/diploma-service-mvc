package com.assistant.api.user.service;

import com.assistant.api.user.data.enums.Role;
import com.assistant.api.user.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final NewAbstractService service;

    @Autowired
    public UserService(NewAbstractService service) {
        this.service = service;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = service.findByEmail(email);
        Set<Role> roles = user.getRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
        CustomUserDetails customUserDetail = new CustomUserDetails();
        customUserDetail.setUser(user);
        customUserDetail.setAuthorities(authorities);
        return customUserDetail;
    }
}
