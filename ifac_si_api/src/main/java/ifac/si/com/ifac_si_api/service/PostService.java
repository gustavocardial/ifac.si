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
import ifac.si.com.ifac_si_api.model.Post.Enum.EPublicacao;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.Enum.EVisibilidade;
import ifac.si.com.ifac_si_api.model.Post.Mapper.PostMapper;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
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

    public Post save(PostRequestDTO postDto, List<MultipartFile> imagens, MultipartFile postCapa) throws Exception {

        Post post = postMapper.toEntity(postDto);

        if (postDto.getCategoriaId() != null) post.setCategoria(buscarCategoria(postDto.getCategoriaId()));

        if (postDto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(postDto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido."));
            post.setUsuario(usuario);
        } else {
            throw new IllegalArgumentException("Usuário deve ser informado.");
        }

        if (postDto.getTags() != null) post.setTags(processarTags(postDto.getTags()));

        processarPostCapa(postCapa).ifPresent(imagemCapa -> {
            post.setImagemCapa(imagemCapa); // Primeiro, adiciona a imagem ao post
            imagemCapa.setPost(post); // Depois, seta o post na imagem
        });

        List<String> urlsImagens = new ArrayList<>();
        if (imagens != null && !imagens.isEmpty()) {
            List<Imagem> imagensList = processarImagens(imagens);
            
            for (Imagem img : imagensList) {
                urlsImagens.add(img.getUrl());
            }

            imagensList.forEach(img -> img.setPost(post));
            post.setImagens(imagensList);
        }

        if (postDto.getVisibilidade() != null) post.setVisibilidade(EVisibilidade.valueOf(postDto.getVisibilidade()));

        if (postDto.getPublicacao() != null) post.setPublicacao(EPublicacao.valueOf(postDto.getPublicacao()));

        if (postDto.getData() != null) {
            post.setData(postDto.getData());
        } else {
            post.setData(LocalDateTime.now());
        }

        String textoFinal = substituirReferenciasPorUrls(postDto.getTexto(), urlsImagens);
        post.setTexto(textoFinal);

        return postRepository.save(post);
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoriaId));
    }

    private List<Tag> processarTags(List<TagDTO> tags) {
        return tags.stream()
            .map(tagDTO -> {
                if (tagDTO.getId() != null) {
                    // Se a tag já tem ID, busca por ID e atualiza o nome
                    return tagRepository.findById(tagDTO.getId())
                            .map(tag -> {
                                tag.setNome(tagDTO.getNome());
                                return tagRepository.save(tag);
                            })
                            .orElseGet(() -> {
                                // Se o ID não existe, trata como nova tag
                                Tag novaTag = new Tag();
                                novaTag.setNome(tagDTO.getNome());
                                return tagRepository.save(novaTag);
                            });
                } else {
                    // Se a tag não tem ID, é uma nova tag, então salva e retorna
                    Tag novaTag = new Tag();
                    novaTag.setNome(tagDTO.getNome());
                    return tagRepository.save(novaTag);
                }
            })
        .collect(Collectors.toList());
    }

    private List<Imagem> processarImagens(List<MultipartFile> imagens) {
        if (imagens == null || imagens.isEmpty()) {
            return new ArrayList<>();
        }

        return imagens.stream()
                .map(imagem -> {
                    try {
                        String fullPath = minIOService.uploadFile(imagem);
                        String fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);
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

    private String substituirReferenciasPorUrls(String texto, List<String> urlsImagens) {
        if (texto == null || urlsImagens.isEmpty()) {
            return texto;
        }

        String textoModificado = texto;
        
        // Para cada URL, substituir a referência correspondente
        for (int i = 0; i < urlsImagens.size(); i++) {
            String referencia = "FILE_" + i;
            String url = urlsImagens.get(i);
            
            // Substituir todas as ocorrências de FILE_X pela URL real
            textoModificado = textoModificado.replace(referencia, url);
            
            System.out.println("Substituindo " + referencia + " por: " + url);
        }
        
        return textoModificado;
    }
    
    @Transactional
    public Post update(Long id, PostRequestDTO postDto, List<MultipartFile> imagens, MultipartFile postCapa) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        // Atualiza campos básicos usando o mapper
        postMapper.updateEntityFromDto(postDto, post);

        // Atualiza categoria se fornecida
        if (postDto.getCategoriaId() != null) post.setCategoria(buscarCategoria(postDto.getCategoriaId()));

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

        processarPostCapa(postCapa).ifPresent(imagemCapa -> {
            post.setImagemCapa(imagemCapa); // Primeiro, adiciona a imagem ao post
            imagemCapa.setPost(post); // Depois, seta o post na imagem
        });


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
        if (postDto.getStatus() != null) post.setStatus(EStatus.valueOf(postDto.getStatus()));
        

        if (postDto.getVisibilidade() != null) post.setVisibilidade(EVisibilidade.valueOf(postDto.getVisibilidade()));

        if (postDto.getPublicacao() != null) post.setPublicacao(EPublicacao.valueOf(postDto.getPublicacao()));

        if (postDto.getData() != null) {
            post.setData(postDto.getData());
        } else {
            post.setData(LocalDateTime.now());
        }

        return postRepository.save(post);
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

    public Post reprovarPost(Long postId, String mensagemReprovacao) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        atualizarStatus(postId, EStatus.REPROVADO);
        post.setMensagemReprovacao(mensagemReprovacao);
        post.setVisibilidade(EVisibilidade.PRIVADO);

        return postRepository.save(post); //Ver sobre o atualizarStatus precisar realmente de salvar o status no repository
    }

    public Post correcaoPost(Long id, PostRequestDTO postDto, List<MultipartFile> imagens, MultipartFile postCapa) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        // Atualiza campos básicos usando o mapper
        postMapper.updateEntityFromDto(postDto, post);

        // Atualiza categoria se fornecida
        if (postDto.getCategoriaId() != null) post.setCategoria(buscarCategoria(postDto.getCategoriaId()));

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

        processarPostCapa(postCapa).ifPresent(imagemCapa -> {
            post.setImagemCapa(imagemCapa); // Primeiro, adiciona a imagem ao post
            imagemCapa.setPost(post); // Depois, seta o post na imagem
        });


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
        post.setStatus(EStatus.CORRECAO);
        
        if (postDto.getVisibilidade() != null && postDto.getVisibilidade().equals("PRIVADO")) post.setVisibilidade(EVisibilidade.valueOf(postDto.getVisibilidade()));
        else post.setVisibilidade(EVisibilidade.PRIVADO);

        if (postDto.getPublicacao() != null) post.setPublicacao(EPublicacao.valueOf(postDto.getPublicacao()));

        if (postDto.getData() != null) {
            post.setData(postDto.getData());
        } else {
            post.setData(LocalDateTime.now());
        }

        return postRepository.save(post);
    }

    public void delete(Long id) {
        Optional<Post> obj = postRepository.findById(id);
        if (obj.isPresent()) {
            Post post = obj.get();
            post.setStatus(EStatus.ARQUIVADO);
            // post.setUsuarioAlteraId(usuarioLogado); 
            postRepository.save(post); 
        }
    }

}
