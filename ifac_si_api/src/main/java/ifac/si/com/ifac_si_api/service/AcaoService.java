package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Acao.Acao;
import ifac.si.com.ifac_si_api.repository.AcaoRepository;

@Service
public class AcaoService implements IService <Acao>{
    
    @Autowired
    private AcaoRepository repo;

    public List<Acao> get() {
        return repo.findAll();
    }

    public Acao get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Acao> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Acao save(Acao objeto) {
        return repo.save(objeto);
    }

    @Override
    public Acao update(Acao objeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
