package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Post;
import ifac.si.com.ifac_si_api.repository.PostRepository;

@Service
public class PostService implements IService<Post>{

    @Autowired
    private PostRepository repo;

    @Override
    public List<Post> get() {
        return repo.findAll();
    }

    @Override
    public Post get(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Post> get(String termoBusca) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Post save(Post objeto) {
        return repo.save(objeto);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
