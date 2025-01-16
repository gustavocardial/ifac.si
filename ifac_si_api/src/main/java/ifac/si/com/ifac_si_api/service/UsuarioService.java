package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Usuario;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository repo;

    public List<Usuario> getAll() {
        return repo.findAll();
    }

    public Usuario get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Usuario> busca(String termoBusca){
        return repo.busca(termoBusca);
    }

    public Usuario save(Usuario objeto) {
        return repo.save(objeto);
    }

    public Usuario update(Usuario objeto) {
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }    
}
