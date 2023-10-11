package security.example.security.service;

import org.springframework.web.multipart.MultipartFile;
import security.example.security.model.NewDto;

import java.io.IOException;
import java.util.List;

public interface NewService {
    NewDto saveNew(String title, String accessToken, MultipartFile file) throws IOException;

    List<NewDto> findAllNew();

    NewDto getNewById(Long id);

//    NewDto updateNew(Long id, String title, String image, String accessToken);

    void deleteNew(Long id, String accessToken);
}
