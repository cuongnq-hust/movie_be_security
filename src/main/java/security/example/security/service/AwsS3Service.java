package security.example.security.service;

import org.springframework.web.multipart.MultipartFile;
import security.example.security.model.request.UploadFileRequest;

import java.io.IOException;

public interface AwsS3Service {
    String uploadFile(MultipartFile file, UploadFileRequest request) throws  IOException;

}