package com.sku.loom.global.service.s3;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Component
@Log4j2
public class S3ServiceImpl implements S3Service {

    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public String uploadS3(MultipartFile file, String type) throws IOException {
        String storedFileName = "";

        if(file != null)
            storedFileName = s3Uploader.uploadFileToS3(file, type);

        return storedFileName;
    }

    @Override
    @Transactional
    public String uploadS3(File file, String type) throws IOException {
        String storedFileName = "";
        if(file != null) {
            storedFileName = s3Uploader.uploadFileToS3(file, type);
        }
        return storedFileName;

    }

    @Override
    @Transactional
    public void deleteS3(String fileUrl) throws Exception {
        if(!Objects.equals(fileUrl, ""))
            s3Uploader.deleteS3(fileUrl);
    }
}