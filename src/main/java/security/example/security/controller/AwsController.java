package security.example.security.controller;

import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.example.security.model.request.UploadFileRequest;
import security.example.security.service.AwsS3ServiceImpl;


@RestController
@RequestMapping("/aws")
@ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
        @io.swagger.annotations.ApiResponse(code = 500, message = "The server is down. Please bear with us."),
}
)
public class AwsController {
    @Autowired
    private  AwsS3ServiceImpl awsS3Service;

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file, @io.swagger.v3.oas.annotations.parameters.RequestBody UploadFileRequest request) {
        try{
            return ResponseEntity.ok().body(awsS3Service.uploadFile(file, request));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DataUtils.generateErrorBaseResponse(Collections.singletonList(ErrorEnum.ERR_400_1.getErrors()))");
        }

    }

}
