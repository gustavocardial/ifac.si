package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.service.TagService;

@RestController
@RequestMapping("/tag")
//@Api(tags = "Tag")
public class TagController{

    @Autowired
    private TagService servico;

    @GetMapping("/")
//    @ApiOperation(value = "Listar todos as tags")
    public ResponseEntity<List<TagDTO>> get() {
        List<TagDTO> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @ApiOperation(value = "Buscar uma tag pelo ID")
    public ResponseEntity<TagDTO> get(@PathVariable("id") Long id) {
        TagDTO registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca/{post}")
//    @ApiOperation(value = "Buscar tags por um Post")
    public ResponseEntity<List<TagDTO>> get(@RequestBody PostRequestDTO postRequestDTO) {
        List<TagDTO> registros = servico.getAllByPost(postRequestDTO);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/busca/{termoBusca}")
//    @ApiOperation(value = "Buscar tags por um termo de busca")
    public ResponseEntity<List<TagDTO>> get(@PathVariable("termoBusca") String termoBusca) {
        List<TagDTO> registros = servico.getAll(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @PostMapping("/")
//    @ApiOperation(value = "Inserir novas tags")
    public ResponseEntity<TagDTO> insert(@RequestBody Tag objeto) {
        TagDTO registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @PutMapping("/")
//    @ApiOperation(value = "Editar tag")
    public ResponseEntity<Tag> update(@RequestBody Tag objeto) {
        Tag registro = servico.update(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Deletar uma tag pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
