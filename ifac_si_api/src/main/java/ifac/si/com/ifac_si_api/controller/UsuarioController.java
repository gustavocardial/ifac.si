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

import ifac.si.com.ifac_si_api.model.Usuario;
import ifac.si.com.ifac_si_api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
//@Api(tags = "Usuário")
public class UsuarioController{

    @Autowired
    private UsuarioService servico;

    @GetMapping("/")
//    @ApiOperation(value = "Listar todos os usuários")
    public ResponseEntity<List<Usuario>> get() {
        List<Usuario> registros = servico.getAll();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @ApiOperation(value = "Buscar um usuário pelo ID")
    public ResponseEntity<Usuario> get(@PathVariable("id") Long id) {
        Usuario registro = servico.get(id);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @GetMapping("/busca/{termoBusca}")
//    @ApiOperation(value = "Buscar usuários por um termo de busca")
    public ResponseEntity<List<Usuario>> get(@PathVariable("termoBusca") String termoBusca) {
        List<Usuario> registros = servico.busca(termoBusca);
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    @PostMapping("/")
//    @ApiOperation(value = "Inserir um novo usuário")
    public ResponseEntity<Usuario> insert(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.CREATED);
    }

    @PutMapping
//    @ApiOperation(value = "Editar usuário")
    public ResponseEntity<Usuario> update(@RequestBody Usuario objeto) {
        Usuario registro = servico.save(objeto);
        return new ResponseEntity<>(registro, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Deletar um usuário pelo ID")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        servico.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
