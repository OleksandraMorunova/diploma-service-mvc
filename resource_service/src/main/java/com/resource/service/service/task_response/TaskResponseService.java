package com.resource.service.service.task_response;

import com.resource.service.model.ResponseTask;
import com.resource.service.repository.task.TaskResponseRepository;
import com.resource.service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskResponseService extends TaskResponseAbstractService<ResponseTask, String> {
    private final GridFsTemplate gridFsTemplate;
    private final FileService fileService;

    @Autowired
    private TaskResponseRepository repository;

    @Autowired
    public TaskResponseService(GridFsTemplate gridFsTemplate, FileService fileService) {
        this.gridFsTemplate = gridFsTemplate;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public void saveTaskResponse(ResponseTask entity, List<MultipartFile> multipartFile) throws IOException {
        entity.setUserId(entity.getUserId());
        entity.setTaskId(entity.getTaskId());
        entity.setText(entity.getText());

        if(multipartFile != null && !multipartFile.isEmpty()){
            List<String> documents = new ArrayList<>();
            for(MultipartFile m: multipartFile){
                documents.add(String.valueOf(fileService.uploadFilesByGridFs(m)));
            }
            entity.setFiles(documents);
        }
        repository.save(entity);
    }

    @Override
    public ResponseTask findResponseTaskByTaskId(String s) {
        return repository.findResponseTaskByTaskId(s);
    }

    @Override
    public void deleteResponseTaskByTaskId(String s) {
        repository.deleteResponseTaskByTaskId(s);
    }
}