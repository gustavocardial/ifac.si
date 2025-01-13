package ifac.si.com.ifac_si_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import ifac.si.com.ifac_si_api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.repository.PostRepository;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import ifac.si.com.ifac_si_api.repository.CategoriaRepository;
import org.springframework.web.multipart.MultipartFile;


@Service
public class PostService{

    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final TagRepository tagRepository;
    private final MinIOService minIOService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository, TagRepository tagRepository, MinIOService minIOService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.tagRepository = tagRepository;
        this.minIOService = minIOService;
        this.postMapper = postMapper;
    }

    public List<Post> get() {
        return postRepository.findAll();
    }

    public List<Post> getPostByStatus(EStatus status) {
        return postRepository.findByStatus(status);
    }

    public Post get(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> get(String termoBusca) {
        return postRepository.busca(termoBusca);
    }

//    @Override
//    public Post save(Post objeto) {
//        return repo.save(objeto);
//    }

    public Post save(PostRequestDTO postDto, List<MultipartFile> imagens) throws Exception {
        // Mapeia DTO para Entidade
        Post post = postMapper.toEntity(postDto);

        // Processa cada imagem no MinIO e cria objeto de Imagem
        List<Imagem> imagemList = new ArrayList<>();
        for (MultipartFile imagem : imagens) {
            String fileName = minIOService.uploadFile(imagem);
            String url = minIOService.getFileUrl("imagens-postagens", fileName);

            Imagem img = new Imagem();
            img.setNomeArquivo(fileName);
            img.setUrl(url);
            img.setDataUpload(LocalDate.now());
            imagemList.add(img);
        }

        post.setImagens(imagemList); // Associa as imagens ao post
        post.setData(LocalDateTime.now()); // Atualiza a data
        Post savedPost = postRepository.save(post); // Salva no banco de dados
        return savedPost;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
    
}
