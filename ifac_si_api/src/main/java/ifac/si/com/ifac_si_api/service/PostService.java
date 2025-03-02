package ifac.si.com.ifac_si_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.exception.ResourceNotFoundException;
import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Usuario;
import ifac.si.com.ifac_si_api.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Post> get(Pageable page) {

        return postRepository.findAll(page);
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

    public Page<Post> get(String termoBusca, Pageable page) {
        return postRepository.busca(termoBusca, page);
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

    public Post save(PostRequestDTO postDto, List<MultipartFile> imagens, MultipartFile postCapa) throws Exception {

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

        processarPostCapa(postCapa).ifPresent(imagemCapa -> {
            post.setImagemCapa(imagemCapa); // Primeiro, adiciona a imagem ao post
            imagemCapa.setPost(post); // Depois, seta o post na imagem
        });

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
        if (imagens == null || imagens.isEmpty()) {
            return new ArrayList<>();
        }

        return imagens.stream()
                .map(imagem -> {
                    try {
                        String fileName = minIOService.uploadFile(imagem);
                        String url = minIOService.getFileUrl("imagens", fileName);

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

    private Optional<Imagem> processarPostCapa(MultipartFile capa) {
        if (capa == null) {
            return Optional.empty();
        }

        try {
            // Realiza o upload da imagem da capa para o MinIO
            String fullPath = minIOService.uploadFile(capa);
            // Extrair apenas o nome do arquivo da URL
            String fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);
            String url = minIOService.getFileUrl("imagens", fileName);
    
            // Retorna o objeto Imagem com as informações da imagem da capa
            Imagem imagemCapa = Imagem.builder()
                    .nomeArquivo(fileName)
                    .url(url)
                    .tamanho(capa.getSize())
                    .dataUpload(LocalDate.now())
                    .build();

            return Optional.of(imagemCapa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar capa do post: " + e.getMessage());
        }
    }

    public List<Post> getPostsPorCategoria(Long categoriaId) {
        return postRepository.findByCategoriaId(categoriaId);
    }

    @Transactional
    public Post update(Long id, PostRequestDTO postDto, List<MultipartFile> imagens, MultipartFile postCapa) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        // Atualiza campos básicos usando o mapper
        postMapper.updateEntityFromDto(postDto, post);

        // Atualiza categoria se fornecida
        if (postDto.getCategoriaId() != null) {
            post.setCategoria(buscarCategoria(postDto.getCategoriaId()));
        }

        // Atualiza usuário se fornecido
        if (postDto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(postDto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido."));
            post.setUsuario(usuario);
        }

        // Atualiza tags
        if (postDto.getTags() != null) {
            post.getTags().clear();
            post.setTags(processarTags(postDto.getTags()));
        }

        if (postCapa != null && !postCapa.isEmpty()) {
            processarPostCapa(postCapa).ifPresent(imagemCapa -> {
                post.setImagemCapa(imagemCapa);
                imagemCapa.setPost(post);
            });
        }        

        // Processa novas imagens se fornecidas
        if (imagens != null && !imagens.isEmpty()) {
            // Remove imagens antigas do MinIO e da entidade
            if (post.getImagens() != null) {
                List<Imagem> imagensAtuais = new ArrayList<>(post.getImagens());
                for (Imagem img : imagensAtuais) {
                    minIOService.deleteFile("imagens", img.getNomeArquivo());
                    post.removeImagem(img);
                }
            }

            // Processa e adiciona novas imagens
            List<Imagem> novasImagens = processarImagens(imagens);
            for (Imagem img : novasImagens) {
                post.addImagem(img);
            }
        }

        // Atualiza status se fornecido
        if (postDto.getStatus() != null) {
            post.setStatus(EStatus.valueOf(postDto.getStatus()));
        }

        return postRepository.save(post);
    }

//    public Post update(Long id, PostRequestDTO postDto, List<MultipartFile> imagens) throws Exception {
//        // Buscar o post existente
//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado com o ID fornecido."));
//
//        // Atualizar título, texto, legenda e status se fornecidos
//        if (postDto.getTitulo() != null) {
//            post.setTitulo(postDto.getTitulo());
//        }
//        if (postDto.getTexto() != null) {
//            post.setTexto(postDto.getTexto());
//        }
//        if (postDto.getLegenda() != null) {
//            post.setLegenda(postDto.getLegenda());
//        }
//        if (postDto.getStatus() != null) {
//            post.setStatus(EStatus.valueOf(postDto.getStatus()));
//        }
//
//        // Atualizar categoria se fornecida
//        if (postDto.getCategoriaId() != null) {
//            post.setCategoria(buscarCategoria(postDto.getCategoriaId()));
//        }
//
//        // Atualizar usuário se fornecido
//        if (postDto.getUsuarioId() != null) {
//            Usuario usuario = usuarioRepository.findById(postDto.getUsuarioId())
//                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido."));
//            post.setUsuario(usuario);
//        }
//
//        // Atualizar tags se fornecidas
//        if (postDto.getTags() != null) {
//            post.setTags(processarTags(postDto.getTags()));
//        }
//
//        // Atualizar imagens se fornecidas
//        if (imagens != null) {
//            List<Imagem> imagensList = processarImagens(imagens);
//
//            // Removendo as imagens antigas se necessário
//            post.getImagens().clear();
//
//            imagensList.forEach(img -> img.setPost(post));
//            post.setImagens(imagensList);
//        }
//
//        // Atualizar a data de modificação
//        post.setData(LocalDateTime.now());
//
//        // Salvar e retornar o post atualizado
//        return postRepository.save(post);
//    }


//    public Post update(Long postId, PostRequestDTO postDto, List<MultipartFile> novasImagens) {
//
//        Post postExistente = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post não encontrado"));
//
//        postExistente.setTitulo(postDto.getTitulo());
//        postExistente.setTexto(postDto.getTexto());
//        postExistente.setLegenda(postDto.getLegenda());
//
//        postExistente.setStatus(EStatus.fromString(postDto.getStatus()));
//
//        if (postDto.getCategoriaId() != null) {
//            Categoria categoria = categoriaRepository.findById(postDto.getCategoriaId())
//                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
//            postExistente.setCategoria(categoria);
//        }
//
//        if (postDto.getTags() != null) {
//            List<Tag> tags = processarTags(postDto.getTags());
//            postExistente.setTags(tags);
//        }
//
//        if (novasImagens != null && !novasImagens.isEmpty()) {
//            List<Imagem> imagensNovas = processarImagens(novasImagens);
//            imagensNovas.forEach(img -> img.setPost(postExistente));
//
//            if (postExistente.getImagens() == null) {
//                postExistente.setImagens(new ArrayList<>());
//            }
//            postExistente.getImagens().addAll(imagensNovas);
//        }
//
//        return postRepository.save(postExistente);
//    }

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


    public void delete(Long id) {
        this.postRepository.deleteById(id);
    }

}
