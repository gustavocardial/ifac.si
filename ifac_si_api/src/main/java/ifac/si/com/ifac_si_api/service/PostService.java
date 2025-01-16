package ifac.si.com.ifac_si_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
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

        List<Post> post = postRepository.findAll();
        return post;
    }

    public List<Post> getByTag(String tag) {
        return postRepository.findPostsByTagName(tag);
    }

    public List<Post> getByStatus(EStatus status) {
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
            try {

                String fileName = minIOService.uploadFile(imagem);
                String url = minIOService.getFileUrl("imagens-postagens", fileName);

                Imagem img = new Imagem();
                img.setNomeArquivo(fileName);
                img.setUrl(url);
                img.setDataUpload(LocalDate.now());
                imagemList.add(img);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar imagem: " + e.getMessage());
            }
        }

        post.setImagens(imagemList); // Associa as imagens ao post
        post.setData(LocalDateTime.now()); // Atualiza a data
        Post savedPost = postRepository.save(post); // Salva no banco de dados
        return savedPost;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post update(Long postId, PostRequestDTO postDto, List<MultipartFile> imagens) throws Exception {
        // Encontra o post existente
        Post existingPost = get(postId);

        // Mapeia o DTO para a entidade e atualiza os campos
        Post updatedPost = postMapper.toEntity(postDto);

        // Atualiza os campos do post existente com os valores do DTO
        existingPost.setTitulo(updatedPost.getTitulo());
        existingPost.setTexto(updatedPost.getTexto());
        existingPost.setLegenda(updatedPost.getLegenda());

        // Atualiza a categoria, se houver alteração
        if (updatedPost.getCategoria() != null) {
            existingPost.setCategoria(updatedPost.getCategoria());
        }

        // Atualiza as tags, se houver alteração
        if (updatedPost.getTags() != null && !updatedPost.getTags().isEmpty()) {
            existingPost.setTags(updatedPost.getTags());
        }

        // Atualiza o status, se houver alteração
        if (updatedPost.getStatus() != null) {
            existingPost.setStatus(updatedPost.getStatus());
        }

        // Atualiza o usuário (caso você permita a modificação do usuário associado)
        if (updatedPost.getUsuario() != null) {
            existingPost.setUsuario(updatedPost.getUsuario());
        }

        // Processa as novas imagens, se houverem
        if (imagens != null && !imagens.isEmpty()) {
            List<Imagem> imagemList = new ArrayList<>();
            for (MultipartFile imagem : imagens) {
                try {
                    String fileName = minIOService.uploadFile(imagem);
                    String url = minIOService.getFileUrl("imagens-postagens", fileName);

                    Imagem img = new Imagem();
                    img.setNomeArquivo(fileName);
                    img.setUrl(url);
                    img.setDataUpload(LocalDate.now());
                    imagemList.add(img);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao processar imagem: " + e.getMessage());
                }
            }
            // Atualiza as imagens associadas ao post
            existingPost.setImagens(imagemList);
        }

        // Atualiza a data de modificação
        existingPost.setData(LocalDateTime.now());

        // Salva o post atualizado no banco de dados
        return postRepository.save(existingPost);
    }

}
