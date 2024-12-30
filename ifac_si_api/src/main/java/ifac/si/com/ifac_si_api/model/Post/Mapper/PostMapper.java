package ifac.si.com.ifac_si_api.model.Post.Mapper;

import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Post.Post;

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
}
