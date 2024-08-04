package ifac.si.com.ifac_si_api.controller;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController implements IController<Categoria> {

    @Autowired
    private CategoriaService servico;

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Categoria>> get() {
        List<Categoria> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> get(@PathVariable("id") Long id) {
        Categoria registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
//        return null;
    }

    @Override
    @GetMapping("/busca/{termoBusca}")
    public ResponseEntity<List<Categoria>> get(String termoBusca) {
        List<Categoria> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<Categoria> insert(@RequestBody Categoria objeto) {
        Categoria registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/")
    public ResponseEntity<Categoria> update(@RequestBody Categoria objeto) {
        Categoria registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
