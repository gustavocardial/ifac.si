package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Usuario;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;

@Service
public class UsuarioService implements IService<Usuario>{

    @Autowired
    private UsuarioRepository repo;

    @Override
    public List<Usuario> get() {
        return repo.findAll();
    }

    @Override
    public Usuario get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> get(String termoBusca) {
        return repo.busca(termoBusca);
    }

    @Override
    public Usuario save(Usuario objeto) {
        return repo.save(objeto);
    }

    @Override
    public Usuario update(Usuario objeto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }    
}
