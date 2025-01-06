package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.service.PostService;

@RestController
@RequestMapping("/post")
//@Tag(name = "Post")
public abstract class PostController implements IController<Post>{

    @Autowired
    private PostService servico;

    @Override
    @GetMapping("/")
//    @Operation(summary = "Listar todos os posts")
    public ResponseEntity<List<Post>> get() {
        List<Post> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public List<Post> getByStatus(@RequestBody EStatus status) {
        List<Post> registros = servico.getPostByStatus(status);
        return new ResponseEntity<>(registros, HttpStatus.OK).getBody();
    }

    @Override
    @GetMapping("/{id}")
//    @Operation(summary = "Buscar post pelo ID")
    public ResponseEntity<Post> get(@PathVariable("id") Long id) {
        Post registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @GetMapping("/busca/{termoBusca}")
//    @Operation(summary = "Buscar posts por um termo de busca")
    public ResponseEntity<List<Post>> get(@PathVariable("id") String termoBusca) {
        List<Post> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @PostMapping("/")
//    @Operation(summary = "Inserir novo post")
    public ResponseEntity<?> insert(@RequestBody PostDTO objeto) {
        Post registro = (Post) servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @Override
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

    @Override
    @DeleteMapping("/{id}")
//    @Operation(summary = "Deletar um post pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
