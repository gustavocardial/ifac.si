package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.model.Post;
import ifac.si.com.ifac_si_api.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController implements IController<Post>{

    @Autowired
    private PostService servico;

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Post>> get() {
        List<Post> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable("id") Long id) {
        Post registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Post>> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<Post> insert(Post objeto) {
        Post registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<Post> update(Post objeto) {
        Post registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
