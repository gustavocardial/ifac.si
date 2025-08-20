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

import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioResponseDTO;
import ifac.si.com.ifac_si_api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário", description = "Gerenciamento de Usuários")
public class UsuarioController{

    @Autowired
    private UsuarioService servico;

    @GetMapping("/")
    @Operation(summary = "Buscar todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> get() {
        List<UsuarioResponseDTO> registros = servico.getAll();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário pelo ID")
    public ResponseEntity<UsuarioResponseDTO> get(@PathVariable("id") Long id) {
        UsuarioResponseDTO registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca/{termoBusca}")
    @Operation(summary = "Buscar usuários por um termo de busca")
    public ResponseEntity<List<UsuarioResponseDTO>> get(@PathVariable("termoBusca") String termoBusca) {
        List<UsuarioResponseDTO> registros = servico.busca(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Inserir um novo usuário")
    public ResponseEntity<Usuario> insert(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @PutMapping("/")
    @Operation(summary = "Atualizar usuário existente")
    public ResponseEntity<Usuario> update(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
