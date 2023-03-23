package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.auth.exceptions.EntityNotFoundException;
import com.assistant.registration_service.user.model_data.enums.Role;
import com.assistant.registration_service.user.model_data.enums.UserStatus;
import com.assistant.registration_service.user.model_data.model.resource_service.ResponseDto;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.service.EncodedData;
import com.assistant.registration_service.auth.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService extends UserAbstractService<User,String> {
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(EntityRepository<User, String> repository, RestTemplate restTemplate){
        super(repository);
        this.restTemplate = restTemplate;
    }

    @Override
    public User findUserByPhone(String phoneNumber){
        return repository.findUserByPhone(EncodedData.encoded(phoneNumber));
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(EncodedData.encoded(email));
    }

    @Override
    public User findAllByStatusAndRolesOrderByName(String status){
        return repository.findAllByStatusAndRolesOrderByName(status, Role.USER.name());
    }

    @Override
    @Transactional
    public User saveUser(User entity){
        Optional<User> u = Optional.ofNullable(findUserByPhone(entity.getPhone()));
        if(u.isEmpty()){
            entity.setName(EncodedData.encoded(entity.getName()));
            entity.setPhone(EncodedData.encoded(entity.getPhone()));
            entity.setStatus(String.valueOf(UserStatus.DEACTIVATED));
            entity.setCode(null); entity.setCodeData(null); entity.setToken(null);
            return repository.save(entity);
        } else throw new ResourceNotFoundException("Email exist: " + entity.getEmail());

    }

    public User updateUser(String phone, User entity){
        Optional<User> u = Optional.ofNullable(findUserByPhone(phone));
        if(u.isPresent()){
            User optional = u.get();
            System.out.println( optional.getPhone());
            optional.setEmail(entity.getEmail() != null ? EncodedData.encoded(entity.getEmail()) : optional.getEmail());
            optional.setPhone(entity.getPhone() != null ? EncodedData.encoded(entity.getPhone()) : optional.getPhone());
            optional.setPassword(entity.getPassword() != null ? EncodedData.encoded(entity.getPassword()) : optional.getPassword());
            return repository.save(optional);
        } else {
            throw new ResourceNotFoundException("Record not found with phone or email");
        }
    }

    public ResponseDto getUser(String email){
        ResponseDto response = new ResponseDto();
        User user = findUserByEmail(email);
        ResponseEntity<TaskDto[]> responseEntity = restTemplate
                .getForEntity("http://RESOURCE-SERVICE:8443/api/v1/task/list/" + user.getId(), TaskDto[].class);
        List<TaskDto> task = Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
        response.setUserDto(user);
        response.setTaskDto(task);
        return response;
    }

    @Override
    public User findUserByEmailOrPhoneAndStatus(String value){
        if(value.contains("@")) {
            Optional<User> userEmail = Optional.ofNullable(findUserByEmail(value));
            if(userEmail.isPresent()){
                if(Objects.equals(userEmail.get().getStatus(), UserStatus.ACTIVE.toString())){
                    return findUserByEmail(value);
                } else throw new EntityNotFoundException(String.class, "Не відповідає значенню", value);
            } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", value);
        }
        else if(value.matches("(?=.*[0-9]+).{4,15}")){
            Optional<User> userPhone = Optional.ofNullable(findUserByPhone(value));
            if(userPhone.isPresent()){
                if(Objects.equals(userPhone.get().getStatus(), UserStatus.DEACTIVATED.toString())) {
                    return findUserByPhone(value);
                } else throw new EntityNotFoundException(String.class, "Не відповідає значенню", value);
            } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", value);
        } else throw new EntityNotFoundException(String.class, "Не відповідає значенню (не номер і не пошта)", value);
    }
}