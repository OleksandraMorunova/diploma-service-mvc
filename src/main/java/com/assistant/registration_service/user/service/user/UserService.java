package com.assistant.registration_service.user.service.user;

import com.assistant.registration_service.auth.exceptions.EntityNotFoundException;
import com.assistant.registration_service.auth.exceptions.ResourceNotFoundException;
import com.assistant.registration_service.auth.service.PasswordEncoder;
import com.assistant.registration_service.user.model_data.enums.Role;
import com.assistant.registration_service.user.model_data.enums.UserStatus;
import com.assistant.registration_service.user.model_data.model.LoadFile;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.model_data.model.resource_service.TaskDto;
import com.assistant.registration_service.user.model_data.model.resource_service.UserAndTasks;
import com.assistant.registration_service.user.model_data.model.resource_service.UsersAndCountTasks;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import com.assistant.registration_service.user.service.task.TaskFeignClientInterface;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import feign.FeignException;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */

@Service
public class UserService extends UserAbstractService<User,String> {
    private final TaskFeignClientInterface clientInterface;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    @Autowired
    public UserService(EntityRepository<User, String> repository, UserEntityRepository userEntityRepository, TaskFeignClientInterface clientInterface, GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations){
        super(repository);
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
        this.userEntityRepository = userEntityRepository;
        this.clientInterface = clientInterface;
    }

    @Override
    public User findUserByPhone(String phoneNumber){
        return userEntityRepository.findUserByPhone(phoneNumber);
    }

    @Override
    public User findUserByEmail(String email) {
        return userEntityRepository.findUserByEmail(email);
    }

    public User findById(String id){
        return userEntityRepository.findUserById(id);
    }

    public UsersAndCountTasks findAll(){
        List<User> userList = userEntityRepository.findAll();
        List<Integer> integerList = new ArrayList<>();
        for(User u : userList){
            List<TaskDto> r = clientInterface.getListOfTasksForUserById(u.getId());
            integerList.add(r == null ? 0 : r.size());
        }
        return new UsersAndCountTasks(userList, integerList);
    }

    @Transactional
    public User saveUser(User entity) {
        Optional<User> u = Optional.ofNullable(findUserByPhone(entity.getPhone()));
        if(u.isEmpty() && entity.getRoles() != null){
            entity.setName(entity.getName());
            entity.setPhone(entity.getPhone());
            entity.setRoles(entity.getRoles());
            entity.setStatus(String.valueOf(UserStatus.DEACTIVATED));
            entity.setUserTokenFirebase(null);
            entity.setCode(null);
            entity.setCodeData(null);
            entity.setToken(null);
            return repository.save(entity);
        } else throw new ResourceNotFoundException("Phone number exist: " + entity.getPhone() + " or don't set roles.");
    }

    public User updateUser(String phone, User entity, MultipartFile multipartFile) throws IOException {
        Optional<User> u;
        if(findUserByPhone(phone) != null) {
            u = Optional.ofNullable(findUserByPhone(phone));
        } else if(repository.findById(phone).isPresent()){
            u = repository.findById(phone);
        } else {
            u = Optional.ofNullable(findUserByEmail(phone));
        }

        if(u.isPresent()){
            User optional = u.get();
            optional.setName((entity.getName() != null) ? entity.getName() : optional.getName());
            optional.setEmail((entity.getEmail() != null) ? entity.getEmail() : optional.getEmail());
            optional.setPhone((entity.getPhone() != null) ? entity.getPhone() : optional.getPhone());
            optional.setPassword((entity.getPassword() != null) ? PasswordEncoder.encode(entity.getPassword()) : optional.getPassword());
            optional.setStatus((entity.getStatus() != null) ? entity.getStatus() : optional.getStatus());
            Set<Role> set = entity.getRoles();
            if (set != null && !set.isEmpty()) {
                optional.setRoles(entity.getRoles());
            } else {
                optional.setRoles(optional.getRoles());
            }

            optional.setToken((entity.getToken() != null) ? entity.getToken() : optional.getToken());
            optional.setUserTokenFirebase((entity.getUserTokenFirebase() != null) ? entity.getUserTokenFirebase() : optional.getUserTokenFirebase());
            if(multipartFile != null && !multipartFile.isEmpty()){
                if(u.get().getIcon() != null){
                    String o = String.valueOf(uploadFilesByGridFs(multipartFile));
                    optional.setIcon(o);
                } else {
                    String newFiles = String.valueOf(uploadFilesByGridFs(multipartFile));
                    optional.setIcon(newFiles);
                }
            }
            return repository.save(optional);
        } else {
            throw new ResourceNotFoundException("Record not found with phone or email");
        }
    }

    public UserAndTasks getUser(String email){
        UserAndTasks response = new UserAndTasks();
        User user;
        if(email.contains("@")){
            user = findUserByEmail(email);
        } else {
            user = userEntityRepository.findUserById(email);
        }

        if(user != null){
           try{
               List<TaskDto> r = clientInterface.getListOfTasksForUserById(user.getId());
               response.setUserDto(user);
               response.setTaskDto(r);
               return response;
           } catch (FeignException e){
               response.setUserDto(user);
               response.setTaskDto(null);
               return response;
           }
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", email);
    }

    public void deleteIconFromUserById(String idFiles, String idTask) {
        Optional<User> findTask = repository.findById(idTask);
        if(findTask.isPresent()){
            if(findTask.get().getIcon() != null) {
                String it = findTask.get().getIcon();
                    if (it.equals(idFiles)){
                        this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(idFiles)));
                        User newUser = findTask.get();
                        newUser.setIcon(null);
                        repository.save(newUser);
                    }
            } else throw new EntityNotFoundException(String.class, "Значення не існує", idFiles.toString());
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", idTask.toString());
    }

    private ObjectId uploadFilesByGridFs(MultipartFile document) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("filename", document.getName());
        metaData.put("size", document.getSize());
        metaData.put("contentType", document.getContentType());
        metaData.put("bytes", document.getBytes());
        return gridFsTemplate.store(document.getInputStream(), document.getOriginalFilename(), metaData);
    }

    public LoadFile download(String isFiles) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(isFiles)) );
        LoadFile loadFile = new LoadFile();
        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename(gridFSFile.getFilename() );
            loadFile.setFileType(gridFSFile.getMetadata().get("contentType").toString() );
            loadFile.setFileSize(gridFSFile.getMetadata().get("size").toString() );
            loadFile.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        } else  throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами");
        return loadFile;
    }
}