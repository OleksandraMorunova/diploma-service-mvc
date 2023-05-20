package com.assistant.registration_service.user.service.task;


import com.assistant.registration_service.user.model_data.model.LoadFile;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESOURCE-SERVICE-FILES",
        url = "http://localhost:8443",
        configuration = ClientConfiguration.class)
public interface FilesFeignClientInterface {
    @GetMapping("/api/v1/files/download/{idFiles}")
    LoadFile download(@Valid @PathVariable("idFiles") String idFiles);

    @DeleteMapping("/api/v1/files/delete/{idTask}/{idFiles}")
    void deleteFileFromTaskById(@Valid @PathVariable("idTask") String idTask, @Valid @PathVariable("idFiles") String idFiles);
}
