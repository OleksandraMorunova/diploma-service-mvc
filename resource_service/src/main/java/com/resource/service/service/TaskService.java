package com.resource.service.service;

import com.mongodb.*;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.resource.service.exceptions.EntityNotFoundException;
import com.resource.service.model.*;
import com.resource.service.repository.EntityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.gridfs.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TaskService extends TaskAbstractService<Task, ObjectId>{
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    @Autowired
    public TaskService(EntityRepository<Task, ObjectId> repository, GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations) {
        super(repository);
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
    }

    @Override
    @Transactional
    public Task saveTask(Task entity, MultipartFile multipartFile) throws IOException {
        entity.setUserId(entity.getUserId());
        entity.setTitle(entity.getTitle());
        entity.setDescription(entity.getDescription());
        if(multipartFile != null && !multipartFile.isEmpty()){
            List<ObjectId> documents = List.of(uploadFilesByGridFs(multipartFile));
            entity.setDocuments(documents);
        }
        return repository.save(entity);
    }

  @Override
  @Transactional
    public Task updateTaskById(ObjectId idTask, Task entity, MultipartFile multipartFile) throws IOException {
        Optional<Task> findTask = Optional.ofNullable(repository.findTaskById(idTask));
        if(findTask.isPresent()){
            Task existTask = findTask.get();
            if(multipartFile != null && !multipartFile.isEmpty()){
                if(findTask.get().getDocuments() != null){
                    List<ObjectId> o = findTask.get().getDocuments();
                    o.add(uploadFilesByGridFs(multipartFile));
                    existTask.setDocuments(o);
                } else {
                    List<ObjectId> newFiles = List.of(uploadFilesByGridFs(multipartFile));
                    existTask.setDocuments(newFiles);
                }
            }
           if(entity != null){
               existTask.setTitle(entity.getTitle() != null ? entity.getTitle() : findTask.get().getTitle());
               existTask.setDescription(entity.getDescription() != null ? entity.getDescription() : findTask.get().getDescription());
               existTask.setUpdateData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
           }
            return repository.save(existTask);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", idTask.toString());
    }

    @Override
    @Transactional
    public Task addCommentById(ObjectId idTask, Comments comments) {
        Optional<Task> findTask = Optional.ofNullable(repository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getComments() != null){
                List<Comments> existList = findTask.get().getComments();
                existList.add(new Comments(comments.getUser_comment_id(), comments.getComment(), comments.getComment_added_data()));
                Task existTask = findTask.get();
                existTask.setComments(existList);
                return repository.save(existTask);
            } else {
                List<Comments> newList = List.of(new Comments(comments.getUser_comment_id(), comments.getComment(), comments.getComment_added_data()));
                Task existTask = findTask.get();
                existTask.setComments(newList);
                return repository.save(existTask);
            }
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", idTask.toString());
    }

    @Override
    public void deleteTaskById(ObjectId idTask) {
        Optional<Task> findTask = Optional.ofNullable(repository.findTaskById(idTask));
        if(findTask.isPresent()){
            List<ObjectId> newListOfDocuments = findTask.get().getDocuments();
            for(ObjectId new_o: newListOfDocuments){
                this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(new_o)));
            }
            repository.deleteById(idTask);
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", idTask.toString());
    }

    @Override
    public void deleteCommentById(ObjectId idTask, ObjectId idUser, ObjectId idComment) {
        Optional<Task> findTask = Optional.ofNullable(repository.findTaskById(idTask));
        if(findTask.isPresent()){
            List<Comments> newListOfComments = findTask.get().getComments();
            if(newListOfComments.size() - 1 >= Integer.parseInt(idComment.toString())){
                if(idUser.toString().equals(newListOfComments.get(Integer.parseInt(idComment.toString())).getUser_comment_id())){
                    newListOfComments.remove(Integer.parseInt(idComment.toString()));
                    Task newTask = findTask.get();
                    newTask.setComments(newListOfComments);
                    repository.save(newTask);
                }  else throw new EntityNotFoundException(String.class, "У вас немає прав", idUser.toString());
            } else throw new EntityNotFoundException(String.class, "Такого повідомлення не існує", idComment.toString());
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами", idTask.toString());
    }

    @Override
    public void deleteFileFromTaskById(ObjectId idFiles, ObjectId idTask) {
        Optional<Task> findTask = Optional.ofNullable(repository.findTaskById(idTask));
        if(findTask.isPresent()){
            if(findTask.get().getDocuments() != null) {
                List<ObjectId> newListOfDocuments = findTask.get().getDocuments();
                for(Iterator<ObjectId> it = newListOfDocuments.iterator(); it.hasNext();) {
                    if (it.next().equals(idFiles)){
                        this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(idFiles)));
                        it.remove();
                        Task newTask = findTask.get();
                        System.out.println(newTask.getDocuments().size());
                        newTask.setDocuments(newTask.getDocuments().size() > 0 ? newListOfDocuments : null);
                        repository.save(newTask);
                    }
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
            loadFile.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()) );
        }
        return loadFile;
    }
}