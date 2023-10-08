package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.example.security.model.NewDto;
import security.example.security.service.impl.NewServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/new")
public class NewDtoController {
    private final NewServiceImpl newService;

    public NewDtoController(NewServiceImpl newService) {
        this.newService = newService;
    }

    @PostMapping("/new")
    public ResponseEntity<NewDto> addReviewToMovie( @RequestBody NewDto newDto, @RequestHeader(name = "Authorization") String accessToken) {
//        System.out.println("id la" + newDto);
//        System.out.println("id la" + accessToken);
        NewDto addNewDto = newService.saveNew(newDto, accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(addNewDto);
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
