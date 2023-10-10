package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.example.security.model.NewDto;
import security.example.security.service.impl.NewServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/new")
public class NewDtoController {
    private final NewServiceImpl newService;

    public NewDtoController(NewServiceImpl newService) {
        this.newService = newService;
    }

    @PostMapping("/new")
    public ResponseEntity<NewDto> addNew(@RequestBody NewDto newDto, @RequestParam("file") MultipartFile file, @RequestHeader(name = "Authorization") String accessToken) throws IOException {
        NewDto addNewDto = newService.saveNew(newDto,accessToken,file);
        return ResponseEntity.status(HttpStatus.CREATED).body(addNewDto);
    }
//    @PostMapping("/update/{id}")
//    public ResponseEntity<NewDto> updateNew(@PathVariable Long id,
//                                            @RequestBody NewDto newDto,
//                                            @RequestHeader("Authorization") String accessToken) {
//        NewDto updatedNewDto = newService.updateNew(id, newDto.getTitle(), newDto.getImage(), accessToken);
//        return ResponseEntity.status(HttpStatus.OK).body(updatedNewDto);
//    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> updateNew(@PathVariable Long id,
                                          @RequestHeader("Authorization") String accessToken) {
        newService.deleteNew(id, accessToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/new")
    public List<NewDto> findAllNew(){
        return newService.findAllNew();
    }
    @GetMapping("/{id}")
    public NewDto findNewDtoByid(@PathVariable Long id){
        return newService.getNewById(id);
    }
}
