package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.model.Profissional.Profissional;
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
     
}
