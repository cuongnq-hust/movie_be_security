package security.example.security.service;

import security.example.security.model.NewDto;

import java.util.List;

public interface NewService {
    NewDto saveNew(NewDto newDto, String accessToken);

    List<NewDto> findAllNew();

    NewDto getNewById(Long id);
}
