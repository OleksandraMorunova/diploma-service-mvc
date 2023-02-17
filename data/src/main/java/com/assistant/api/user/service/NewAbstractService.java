package com.assistant.api.user.service;

import com.assistant.api.user.data.enums.UserStatus;
import com.assistant.api.user.data.model.User;
import com.assistant.api.user.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class NewAbstractService extends AbstractService<User,String>{

    @Autowired
    public NewAbstractService(UserEntityRepository repository) {
        super(repository);
    }

    @Override
    public void save(User entity) {
        Optional<User> optionalUser = repository.findUserByEmail(entity.getEmail());
        if (optionalUser.isEmpty()) {
            entity.setPassword(Base64.getEncoder().encodeToString(entity.getPassword().getBytes()));
            entity.setStatus(String.valueOf(UserStatus.DEACTIVATED));
            repository.save(entity);
        }
        else {
            throw new ResourceNotFoundException("Email exist: " + entity.getEmail());
        }
    }

    @Override
    public User update(User entity) {
        Optional<User> userOptional = repository.findUserByEmail(entity.getEmail());
        if(userOptional.isPresent()){
            User newUser = userOptional.get();
            newUser.setFirstname(entity.getFirstname());
            newUser.setLastname(entity.getLastname());
            newUser.setEmail(entity.getEmail());
            newUser.setPhone(entity.getPhone());
            newUser.setPassword(Base64.getEncoder().encodeToString(entity.getPassword().getBytes()));
            return newUser;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + entity.getEmail());
        }
    }
}
