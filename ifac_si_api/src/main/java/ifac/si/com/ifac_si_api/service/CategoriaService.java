package ifac.si.com.ifac_si_api.service;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements IService<Categoria>{

    @Autowired
    private CategoriaRepository repo;

    @Override
    public List<Categoria> get() {
        return repo.findAll();
    }

    @Override
    public Categoria get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Categoria> get(String termoBusca) {
        return repo.busca(termoBusca);
    }

    @Override
    public Categoria save(Categoria objeto) {
        return repo.save(objeto);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}