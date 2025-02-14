package ifac.si.com.ifac_si_api.model.Post.Mapper;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Post;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;

@Service
public class PostMapper {

    public PostDTO toDTO(Post post) {

        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setUsuarioId(post.getUsuario().getId());
        postDTO.setCategoriaId(post.getCategoria().getId());
        postDTO.setTitulo(post.getTitulo());
        postDTO.setTexto(post.getTexto());
        postDTO.setData(post.getData());
        postDTO.setLegenda(post.getLegenda());
        if ( post.getStatus() != null ) postDTO.setStatus(post.getStatus().name());
        if ( post.getStatus() != null ) postDTO.setImagens(post.getImagens());
        postDTO.setImagemCapa(post.getImagemCapa());

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
        post.setStatus(EStatus.fromString(postDto.getStatus()));
        post.setImagemCapa(postDto.getImagemCapa());

        return post;
    }

    public void updateEntityFromDto(PostRequestDTO postDto, Post post) {
    }
}
