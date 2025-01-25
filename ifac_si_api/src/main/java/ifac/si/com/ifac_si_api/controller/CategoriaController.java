package ifac.si.com.ifac_si_api.controller;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "Gerenciamento de Categoria")
public class CategoriaController implements IController<Categoria> {

    @Autowired
    private CategoriaService servico;

    @Override
    @GetMapping("/")
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<Categoria>> get() {
        List<Categoria> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma categoria pelo ID")
    public ResponseEntity<Categoria> get(@PathVariable("id") Long id) {
        Categoria registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
//        return null;
    }

    @Override
    @GetMapping("/busca/{termoBusca}")
    @Operation(summary = "Buscar todas as categorias por um termo de busca")
    public ResponseEntity<List<Categoria>> get(@PathVariable("termoBusca") String termoBusca) {
        List<Categoria> registros = servico.get(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @PostMapping("/")
//    @ApiOperation(value = "Inserir uma nova categoria")
    public ResponseEntity<Categoria> insert(@RequestBody Categoria objeto) {
        Categoria registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/")
//    @ApiOperation(value = "Editar categoria")
    public ResponseEntity<Categoria> update(@RequestBody Categoria objeto) {
        Categoria registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Deletar uma categoria pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
