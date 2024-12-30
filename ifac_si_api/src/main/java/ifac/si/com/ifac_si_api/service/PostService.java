package ifac.si.com.ifac_si_api.service;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.repository.PostRepository;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import ifac.si.com.ifac_si_api.repository.CategoriaRepository;


@Service
public abstract class PostService implements IService{

    @Autowired
    private PostRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PostMapper postMapper;

    public List<Post> get() {
        return repo.findAll();
    }

    public List<Post> getPostByStatus(EStatus status) {
        return repo.findByStatus(status);
    }

    public Post get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Post> get(String termoBusca) {
        return repo.busca(termoBusca);
    }

//    @Override
//    public Post save(Post objeto) {
//        return repo.save(objeto);
//    }

    public Object save(PostDTO objeto){

        Post post = new Post();

        post.setTitulo(objeto.getTitulo());
        post.setUsuario(usuarioRepository.findById(objeto.getUsuarioId()).orElseThrow());
        post.setCategoria(categoriaRepository.findById(objeto.getCategoriaId()).orElseThrow());
        post.setTexto(objeto.getTexto());
        post.setData(objeto.getData());
        post.setLegenda(objeto.getLegenda());

        post.setStatus(EStatus.valueOf(objeto.getStatus().toString().toUpperCase()));

        Post salvo = repo.save(post);


        return postMapper.toDTO(salvo);

    }


    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
    
}
