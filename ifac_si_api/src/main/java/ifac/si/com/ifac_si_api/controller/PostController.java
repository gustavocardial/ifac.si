package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.service.PostService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
//@Tag(name = "Post")
public class PostController{

    @Autowired
    private PostService servico;

    @GetMapping("/")
//    @Operation(summary = "Listar todos os posts")
    public ResponseEntity<List<Post>> get() {
        List<Post> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public List<Post> getByStatus(@RequestParam EStatus status) {
        List<Post> registros = servico.getByStatus(status);
        return new ResponseEntity<>(registros, HttpStatus.OK).getBody();
    }

    @GetMapping("/{id}")
//    @Operation(summary = "Buscar post pelo ID")
    public ResponseEntity<Post> get(@PathVariable("id") Long id) {
        Post registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca")
//    @Operation(summary = "Buscar posts por um termo de busca")
    public ResponseEntity<List<Post>> get(@RequestParam String termoBusca) {
        List<Post> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Inserir novo post")
    public ResponseEntity<?> insert(@RequestBody PostRequestDTO objeto, @Parameter(
            description = "Arquivo para upload",
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
    )
    @RequestParam("file") List<MultipartFile> file) throws Exception {
        Post registro = (Post) servico.save(objeto, file);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Post> update(Post objeto) {
        return null;
    }

//    @Override
//    @PutMapping("/")
//    @Operation(summary = "Editar post")
//    public ResponseEntity<Post> update(@RequestBody Post objeto) {
//        Post registro = servico.update(objeto);
//        return new ResponseEntity<>(registro, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
//    @Operation(summary = "Deletar um post pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
