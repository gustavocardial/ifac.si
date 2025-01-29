package ifac.si.com.ifac_si_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.repository.PostRepository;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import ifac.si.com.ifac_si_api.repository.CategoriaRepository;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Transactional
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

    public Page<Post> getAllPosts(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.getAllPosts(pageable);
    }

    public List<Post> get() {

        return postRepository.findAll();
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

//    public Post save(PostRequestDTO postDto, List<MultipartFile> imagens) throws Exception {
//        // Mapeia DTO para Entidade
//        Post post = postMapper.toEntity(postDto);
//
//        // Processa cada imagem no MinIO e cria objeto de Imagem
//        List<Imagem> imagemList = new ArrayList<>();
//        for (MultipartFile imagem : imagens) {
//            try {
//
//                String fileName = minIOService.uploadFile(imagem);
//                String url = minIOService.getFileUrl("imagens-postagens", fileName);
//
//                Imagem img = new Imagem();
//                img.setNomeArquivo(fileName);
//                img.setUrl(url);
//                img.setDataUpload(LocalDate.now());
//                imagemList.add(img);
//            } catch (Exception e) {
//                throw new RuntimeException("Erro ao processar imagem: " + e.getMessage());
//            }
//        }
//
//        post.setImagens(imagemList); // Associa as imagens ao post
//        post.setData(LocalDateTime.now()); // Atualiza a data
//        Post savedPost = postRepository.save(post); // Salva no banco de dados
//        return savedPost;
//    }
//
//    public void delete(Long id) {
//        postRepository.deleteById(id);
//    }

    public Post save(PostRequestDTO postDto, List<MultipartFile> imagens) throws Exception {

        Post post = postMapper.toEntity(postDto);

        if (postDto.getCategoriaId() != null) {
            post.setCategoria(buscarCategoria(postDto.getCategoriaId()));
        }

        if (postDto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(postDto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido."));
            post.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("Usuário deve ser informado.");
        }

        if (postDto.getTags() != null) {
            post.setTags(processarTags(postDto.getTags()));
        }

        if (imagens != null) {
            List<Imagem> imagensList = processarImagens(imagens);
            imagensList.forEach(img -> img.setPost(post));
            post.setImagens(imagensList);
        }

        post.setData(LocalDateTime.now());
        return postRepository.save(post);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoriaId));
    }

    private List<Tag> processarTags(List<String> tagNames) {
        return tagNames.stream()
                .map(nome -> tagRepository.findByNome(nome)
                        .orElseGet(() -> {
                            Tag novaTag = new Tag();
                            novaTag.setNome(nome);
                            return tagRepository.save(novaTag);
                        }))
                .collect(Collectors.toList());
    }

    private List<Imagem> processarImagens(List<MultipartFile> imagens) {
        return imagens.stream()
                .map(imagem -> {
                    try {
                        String fileName = minIOService.uploadFile(imagem);
                        String url = minIOService.getFileUrl("imagens-postagens", fileName);

                        return Imagem.builder()
                                .nomeArquivo(fileName)
                                .url(url)
                                .tamanho(imagem.getSize())
                                .dataUpload(LocalDate.now())
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao processar imagem: " + e.getMessage());
                    }
                })
                .collect(Collectors.toList());

    }

    public List<Post> getPostsPorCategoria(Long categoriaId) {
        return postRepository.findByCategoriaId(categoriaId);
    }

    public Post update(Long postId, PostRequestDTO postDto, List<MultipartFile> novasImagens) {

        Post postExistente = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        postExistente.setTitulo(postDto.getTitulo());
        postExistente.setTexto(postDto.getTexto());
        postExistente.setLegenda(postDto.getLegenda());

        postExistente.setStatus(EStatus.fromString(postDto.getStatus()));

        if (postDto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(postDto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            postExistente.setCategoria(categoria);
        }

        if (postDto.getTags() != null) {
            List<Tag> tags = processarTags(postDto.getTags());
            postExistente.setTags(tags);
        }

        if (novasImagens != null && !novasImagens.isEmpty()) {
            List<Imagem> imagensNovas = processarImagens(novasImagens);
            imagensNovas.forEach(img -> img.setPost(postExistente));

            if (postExistente.getImagens() == null) {
                postExistente.setImagens(new ArrayList<>());
            }
            postExistente.getImagens().addAll(imagensNovas);
        }

        return postRepository.save(postExistente);
    }

    public void removerImagem(Long postId, Long imagemId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        post.getImagens().removeIf(imagem -> imagem.getId().equals(imagemId));

//        try {
//            // Aqui você pode adicionar a lógica para deletar o arquivo do MinIO
//            minIOService.deleteFile("imagens-postagens", imagem.getNomeArquivo());
//        } catch (Exception e) {
//            log.error("Erro ao deletar arquivo do MinIO", e);
//        }

        postRepository.save(post);
    }

    public Post atualizarStatus(Long postId, EStatus novoStatus) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        post.setStatus(novoStatus);
        return postRepository.save(post);
    }

//    public Page<PostDTO> findAll(String titulo, Long categoriaId, Long usuarioId, EStatus status, Pageable pageable) {
//
//        Specification<Post> spec = Specification.where(null);
//
//        if (titulo != null && !titulo.isEmpty()) {
//            spec = spec.and((root, query, cb) ->
//                    cb.like(cb.lower(root.get("titulo")),
//                            "%" + titulo.toLowerCase() + "%"));
//        }
//
//        if (categoriaId != null) {
//            spec = spec.and((root, query, cb) ->
//                    cb.equal(root.get("categoria").get("id"), categoriaId));
//        }
//
//        if (usuarioId != null) {
//            spec = spec.and((root, query, cb) ->
//                    cb.equal(root.get("usuario").get("id"), usuarioId));
//        }
//
//        if (status != null) {
//            spec = spec.and((root, query, cb) ->
//                    cb.equal(root.get("status"), status));
//        }
//
//        Page<Post> postsPage = postRepository.findAll(spec, pageable);
//        return postsPage.map(postMapper::toDTO);
//    }


//    public void delete(Long id) {
//    }

}
