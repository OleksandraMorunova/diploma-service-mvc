package com.resource.service.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.resource.service.exceptions.EntityNotFoundException;
import com.resource.service.model.LoadFile;
import com.resource.service.model.Task;
import com.resource.service.repository.EntityRepository;
import com.resource.service.repository.task.TaskRepository;
import com.resource.service.service.task.TaskAbstractService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService extends TaskAbstractService<Task, String> {
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    @Autowired
    public FileService(EntityRepository<Task, String> repository, TaskRepository taskRepository, GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations) {
        super(repository, taskRepository);
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
    }

    public ObjectId uploadFilesByGridFs(MultipartFile document) throws IOException {
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
        } else throw new EntityNotFoundException(String.class, "Значення не існує зі вказаними параметрами");
        return loadFile;
    }
}