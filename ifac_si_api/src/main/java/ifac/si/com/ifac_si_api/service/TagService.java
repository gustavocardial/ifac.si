package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ifac.si.com.ifac_si_api.model.Tag;
import ifac.si.com.ifac_si_api.repository.TagRepository;

public class TagService implements IService<Tag>{

    @Autowired
    private TagRepository repo;

    @Override
    public List<Tag> get() {
        return repo.findAll();
    }

    @Override
    public Tag get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Tag> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");

        //Ver no repositório porque precisa query, vou montar a estrutura básica primeiro
    }

    @Override
    public Tag save(Tag objeto) {
        return repo.save(objeto);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
