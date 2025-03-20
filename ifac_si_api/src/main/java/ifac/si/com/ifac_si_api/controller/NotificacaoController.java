package ifac.si.com.ifac_si_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifac.si.com.ifac_si_api.exception.ResourceNotFoundException;
import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
import ifac.si.com.ifac_si_api.service.NotificacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/notificacoes")
@Tag(name = "Notificações", description = "API para gerenciamento de notificações")
public class NotificacaoController {
    
    @Autowired
    private NotificacaoService servico;

    @GetMapping("/")
    public ResponseEntity<List<Notificacao>> listarNotificacoes() {
        List<Notificacao> registros = servico.listarNotificacoes();
        return new ResponseEntity<>(registros, HttpStatus.OK);
    }

    // @PostMapping("/")
    // public ResponseEntity<>(Notificacao> postMethodN)me(@RequestBody Notificacao objeto) {
       
    //     return entity;
    // }
    
    @PutMapping("/{id}/lida")
    public ResponseEntity<Notificacao> marcarComoLida(@PathVariable Long id) {
        try {
            Notificacao notificacao = servico.marcarComoLida(id);
            return new ResponseEntity<>(notificacao, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
