package ifac.si.com.ifac_si_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class Teste implements IController{

    @Override
    @GetMapping("/")
    public ResponseEntity<List> get() {
        return null;
    }

    @Override
    public ResponseEntity get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List> get(String termoBusca) {
        return null;
    }

    @Override
    public ResponseEntity<?> insert(Object objeto) {
        return null;
    }

    @Override
    public ResponseEntity update(Object objeto) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        return null;
    }
}
