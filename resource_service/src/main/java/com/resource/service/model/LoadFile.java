package com.resource.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoadFile {
    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
