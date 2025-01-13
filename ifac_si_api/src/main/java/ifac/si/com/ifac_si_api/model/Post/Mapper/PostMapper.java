package ifac.si.com.ifac_si_api.model.Post.Mapper;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostRequestDTO;
import ifac.si.com.ifac_si_api.model.Post.Post;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        if ( post.getStatus() != null )postDTO.setImagens(post.getImagens());

        return postDTO;
    }

    public Post toEntity(PostRequestDTO postDto) {

        Post post = new Post();
        post.setTitulo(postDto.getTitulo());
        post.setTexto(postDto.getTexto());
        post.setLegenda(postDto.getLegenda());
        post.setStatus(postDto.getStatus() != null ? EStatus.valueOf(postDto.getStatus()) : EStatus.RASCUNHO);

        return post;
    }
}
