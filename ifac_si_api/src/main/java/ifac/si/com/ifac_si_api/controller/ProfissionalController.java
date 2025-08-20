package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ifac.si.com.ifac_si_api.model.Profissional.Profissional;
import ifac.si.com.ifac_si_api.model.Profissional.DTO.ProfissionalDTO;
import ifac.si.com.ifac_si_api.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/profissional")
@Tag(name = "Profissional", description = "Gerenciamento de Profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService servico;

    @GetMapping("/")
    @Operation(summary = "Listar todos os profissionais")
    public ResponseEntity<List<Profissional>> get() {
        List<Profissional> regitstros = servico.get();
        return new ResponseEntity<>(regitstros, HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar post pelo Id")
    public ResponseEntity<Profissional> get(@PathVariable("id") Long id) {
        Profissional registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca/{termoBusca}")
    @Operation(summary = "Buscar ações por um termo de busca")
    public ResponseEntity<List<Profissional>> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Operation(summary = "Inserir novo profissional")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insert(ProfissionalDTO objeto, @RequestParam(value = "perfil", required = true) MultipartFile perfilFile) {
        Profissional registro = servico.save(objeto, perfilFile);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @Operation(summary = "Atualizar profissional existente")
    public ResponseEntity<Profissional> update(Profissional objeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar profissional pelo Id")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
     
}
