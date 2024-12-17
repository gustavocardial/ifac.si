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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.model.Tag;
import ifac.si.com.ifac_si_api.service.TagService;

@RestController
@RequestMapping("/tag")
//@Api(tags = "Tag")
public class TagController implements IController<Tag>{

    @Autowired
    private TagService servico;

    @Override
    @GetMapping("/")
//    @ApiOperation(value = "Listar todos as tags")
    public ResponseEntity<List<Tag>> get() {
        List<Tag> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
//    @ApiOperation(value = "Buscar uma tag pelo ID")
    public ResponseEntity<Tag> get(@PathVariable("id") Long id) {
        Tag registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @GetMapping("/busca/{termoBusca}")
//    @ApiOperation(value = "Buscar tags por um termo de busca")
    public ResponseEntity<List<Tag>> get(@PathVariable("termoBusca") String termoBusca) {
        List<Tag> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @PostMapping("/")
//    @ApiOperation(value = "Inserir novas tags")
    public ResponseEntity<Tag> insert(@RequestBody Tag objeto) {
        Tag registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/")
//    @ApiOperation(value = "Editar tag")
    public ResponseEntity<Tag> update(@RequestBody Tag objeto) {
        Tag registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Deletar uma tag pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
