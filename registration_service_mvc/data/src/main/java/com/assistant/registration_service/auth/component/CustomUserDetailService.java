package com.assistant.registration_service.auth.component;

import com.assistant.registration_service.user.model_data.enums.Role;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.EncodedData;
import com.assistant.registration_service.user.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserService service;

    public CustomUserDetailService(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(service.findUserByEmail(username));
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder;
        EncodedData encodedData = new EncodedData();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(encodedData.encoded(user.getPassword()));
            List<Role> roleList = new ArrayList<>(user.getRoles());
            String[] roles = new String[roleList.size()];
            for (int i = 0; i < roleList.size(); i++) {
                roles[i] = roleList.get(i).getAuthority();
            }
            userBuilder.authorities(roles);
        } else {
            throw new UsernameNotFoundException("User does not exist");
        }

        return userBuilder.build();
    }
}

