package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Acoes;
import ifac.si.com.ifac_si_api.repository.AcoesRepository;

@Service
public class AcoesService implements IService <Acoes>{
    
    @Autowired
    private AcoesRepository repo;

    public List<Acoes> get() {
        return repo.findAll();
    }

    public Acoes get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Acoes> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Acoes save(Acoes objeto) {
        return repo.save(objeto);
    }

    @Override
    public Acoes update(Acoes objeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
