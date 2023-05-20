package com.resource.service.service.task;

import com.resource.service.exceptions.EntityNotFoundException;
import com.resource.service.model.*;
import com.resource.service.repository.EntityRepository;
import com.resource.service.repository.task.TaskRepository;
import com.resource.service.service.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.gridfs.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TaskService extends TaskAbstractService<Task, String> {
    private final GridFsTemplate gridFsTemplate;
    private final FileService service;

    @Autowired
    public TaskService(EntityRepository<Task, String> repository, TaskRepository taskRepository, GridFsTemplate gridFsTemplate, FileService service) {
        super(repository, taskRepository);
        this.gridFsTemplate = gridFsTemplate;
        this.service = service;
    }

    @Override
    public void saveTask(Task entity, List<MultipartFile> multipartFile) throws IOException {
        entity.setUserId(entity.getUserId());
        entity.setTitle(entity.getTitle());
        entity.setAudit(CheckTask.NOT_REVIEWED.getCheckLine());
        if(entity.getDescription() == null) entity.setDescription(null);
        else entity.setDescription(entity.getDescription());

        if(multipartFile != null && !multipartFile.isEmpty()){
            List<String> documents = new ArrayList<>();
            for(MultipartFile m: multipartFile){
                documents.add(String.valueOf(service.uploadFilesByGridFs(m)));
            }
            entity.setFiles(documents);
        }
        repository.save(entity);
    }

  @Override
    public Task updateTaskById(String idTask, Task entity, List<MultipartFile> multipartFile) throws IOException {
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            Task existTask = findTask.get();
            if(multipartFile != null && !multipartFile.isEmpty()){
                if(findTask.get().getFiles() != null){
                    List<String> o = findTask.get().getFiles();
                    for(MultipartFile m: multipartFile){
                        o.add(String.valueOf(service.uploadFilesByGridFs(m)));
                    }
                    existTask.setFiles(o);
                } else {
                    List<String> newFiles = new ArrayList<>();
                    for(MultipartFile m: multipartFile) {
                        newFiles.add(String.valueOf(service.uploadFilesByGridFs(m)));
                    }
                    existTask.setFiles(newFiles);
                }
            }
           if(entity != null){
               existTask.setTitle(entity.getTitle() != null ? entity.getTitle() : findTask.get().getTitle());
               existTask.setDescription(entity.getDescription() != null ? entity.getDescription() : findTask.get().getDescription());
               existTask.setUpdateData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
               existTask.setAudit(entity.getAudit());
           }
            return repository.save(existTask);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idTask);
    }

    public List<Task> getListOfComments(){
        return repository.findAll();
    }

    @Override
    @Transactional
    public Task addCommentById(String idTask, Comments comments) {
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getComments() != null){
                List<Comments> existList = findTask.get().getComments();
                existList.add(new Comments(String.valueOf(ObjectId.get()), comments.getUser_comment_id(), comments.getComment(), comments.getComment_added_data(), false));
                Task existTask = findTask.get();
                existTask.setComments(existList);
                return repository.save(existTask);
            } else {
                List<Comments> newList = List.of(new Comments(String.valueOf(ObjectId.get()), comments.getUser_comment_id(), comments.getComment(), comments.getComment_added_data(), false));
                Task existTask = findTask.get();
                existTask.setComments(newList);
                return repository.save(existTask);
            }
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ");
    }

    public Task updateComment(String idTask, String idComment){
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getComments() != null){
                List<Comments> existList = findTask.get().getComments();
                for(Comments c: existList){
                    if(c.getId().equals(idComment)) c.setReviewed(true);
                }
                Task existTask = findTask.get();
                existTask.setComments(existList);
                return repository.save(existTask);
            } else throw new EntityNotFoundException(String.class, "Коментаря не існує зі вказаними параметрами: ", idTask);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idTask);
    }

    @Override
    public void deleteTaskById(String idTask) {
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getFiles() != null){
                List<String> newListOfDocuments = findTask.get().getFiles();
                for(String new_o: newListOfDocuments){
                    this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(new_o)));
                }
            }
            repository.deleteById(idTask);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idTask);
    }

    @Override
    public List<Task> findAllTaskById(String userId) {
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteCommentById(String idTask, String idUser, String idComment) {
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            List<Comments> newListOfComments = findTask.get().getComments();
            if(newListOfComments.size() - 1 >= Integer.parseInt(idComment)){
                if(idUser.equals(newListOfComments.get(Integer.parseInt(idComment)).getUser_comment_id())){
                    newListOfComments.remove(Integer.parseInt(idComment));
                    Task newTask = findTask.get();
                    newTask.setComments(newListOfComments);
                    repository.save(newTask);
                }  else throw new EntityNotFoundException(String.class, "У вас немає прав: ", idUser);
            } else throw new EntityNotFoundException(String.class, "Такого повідомлення не існує: ", idComment);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idTask);
    }

    @Override
    public void deleteFileFromTaskById(String idFiles, String idTask) {
        Optional<Task> findTask = Optional.ofNullable(taskRepository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getFiles() != null) {
                List<String> newListOfDocuments = findTask.get().getFiles();
                for (String it: newListOfDocuments){
                    if (it.equals(idFiles.toString())){
                        this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(idFiles)));
                        Task newTask = findTask.get();
                        newTask.setFiles(newTask.getFiles().size() > 0 ? newListOfDocuments : null);
                        repository.save(newTask);
                    }
                }
            } else throw new EntityNotFoundException(String.class, "Значення не існує: ", idFiles);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами: ", idTask);
    }
 }