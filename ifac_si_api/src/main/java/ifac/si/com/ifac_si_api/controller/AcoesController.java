package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import ifac.si.com.ifac_si_api.model.Acoes;
import ifac.si.com.ifac_si_api.service.AcoesService;

@RestController
@RequestMapping("/acoes")
@Tag(name = "acoes", description = "Gerenciamento de Ações")
public class AcoesController implements IController<Acoes>{
    
    @Autowired
    private AcoesService servico;

    @Override
    @GetMapping("/")
    @Operation(summary = "Buscar todas ações")
    public ResponseEntity<List<Acoes>> get() {
        List<Acoes> registros = servico.get();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma ação por Id")
    public ResponseEntity<Acoes> get(@PathVariable("id") Long id) {
        Acoes registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @GetMapping("/busca/{termoBusca}")
    @Operation(summary = "Buscar ações por um termo de busca")
    public ResponseEntity<List<Acoes>> get(@PathVariable("termoBusca") String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    @PostMapping("/")
    @Operation(summary = "Inserir nova ação")
    public ResponseEntity<Acoes> insert(Acoes objeto) {
        Acoes registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @PutMapping("/")
    @Operation(summary = "Atualizar ação existente")
    public ResponseEntity<Acoes> update(Acoes objeto) {
        Acoes registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ação pelo Id")
    public ResponseEntity<?> delete(Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
