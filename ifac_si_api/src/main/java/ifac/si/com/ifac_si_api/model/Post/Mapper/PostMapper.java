package ifac.si.com.ifac_si_api.model.Post.Mapper;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Post.Enum.EPublicacao;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Post.Enum.EVisibilidade;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.model.Usuario.Mapper.UsuarioMapper;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;

@Service
public class PostMapper {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public PostDTO toDTO(Post post) {

        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        if (post.getUsuario() != null) {
            postDTO.setUsuario(UsuarioMapper.toUsuarioResponseDTO(post.getUsuario()));
        }
        
        postDTO.setTitulo(post.getTitulo());
        postDTO.setTexto(post.getTexto());
        postDTO.setData(post.getData());
        postDTO.setLegenda(post.getLegenda());
        postDTO.setVisibilidade(post.getVisibilidade().name());
        postDTO.setPublicacao(post.getPublicacao().name());
        postDTO.setMensagemReprovacao(post.getMensagemReprovacao());
        postDTO.setImagemCapa(post.getImagemCapa());
        if (post.getCategoria() != null) {
            postDTO.setCategoriaId(post.getCategoria().getId());
        }
        if ( post.getStatus() != null ) postDTO.setStatus(post.getStatus().name());
        if (post.getImagens() != null) postDTO.setImagens(post.getImagens());

        return postDTO;
    }

    public List<PostDTO> toListOfDTO(List<Post> posts) {
        if (posts == null) {
            return new ArrayList<>();
        }

        return posts.stream()
                .map(this::toDTO)  // Para cada Post, converte para PostDTO
                .collect(Collectors.toList());
    }

    public Post toEntity(PostRequestDTO postDto) {

        Post post = new Post();
        post.setTitulo(postDto.getTitulo());
        post.setTexto(postDto.getTexto());
        post.setLegenda(postDto.getLegenda());
        post.setMensagemReprovacao(postDto.getMensagemReprovacao());
        post.setStatus(EStatus.fromString(postDto.getStatus()));
        post.setVisibilidade(EVisibilidade.fromString(postDto.getVisibilidade()));
        post.setPublicacao(EPublicacao.fromString(postDto.getPublicacao()));
        if (postDto.getData() != null) {
            post.setData(postDto.getData());
        }
        return post;
    }

    public void updateEntityFromDto(PostRequestDTO postDto, Post post) {
        if (postDto.getTitulo() != null) {
            post.setTitulo(postDto.getTitulo());
        }
        if (postDto.getTexto() != null) {
            post.setTexto(postDto.getTexto());
        }
        if (postDto.getLegenda() != null) {
            post.setLegenda(postDto.getLegenda());
        }
        if (postDto.getStatus() != null) {
            post.setStatus(EStatus.valueOf(postDto.getStatus()));
        }
        if (postDto.getVisibilidade() != null) {
            post.setVisibilidade(EVisibilidade.valueOf(postDto.getVisibilidade()));
        }
        if (postDto.getData() != null) {
            post.setData(postDto.getData());
        }
        if (postDto.getPublicacao() != null) {
            post.setPublicacao(EPublicacao.valueOf(postDto.getPublicacao()));
        }
    }
}
