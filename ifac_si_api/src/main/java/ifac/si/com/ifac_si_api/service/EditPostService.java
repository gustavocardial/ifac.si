package ifac.si.com.ifac_si_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.EditPost.EditPost;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.repository.EditPostRepository;
import jakarta.transaction.Transactional;

public class EditPostService {
   
    @Autowired
    private EditPostRepository repo;

    @Transactional
    public EditPost criarCopiaPost(Post post) {
        // Criar um novo EditPost com os dados do post original
        EditPost editPost = new EditPost();
        
        // Definir o ID original
        editPost.setIdOriginal(post.getId());
        
        // Copiar propriedades básicas
        editPost.setTitulo(post.getTitulo());
        editPost.setTexto(post.getTexto());
        editPost.setLegenda(post.getLegenda());
        editPost.setData(post.getData());
        editPost.setStatus(post.getStatus());
        
        // Copiar relacionamentos
        editPost.setUsuario(post.getUsuario());
        editPost.setCategoria(post.getCategoria());
        editPost.setTags(post.getTags());
        
        // Copiar imagens
        if (post.getImagens() != null) {
            List<Imagem> copiaImagens = new ArrayList<>();
            for (Imagem img : post.getImagens()) {
                // Aqui você pode decidir se quer criar cópias profundas ou apenas referências
                copiaImagens.add(img);
            }
            editPost.setImagens(copiaImagens);
        }
        
        if (post.getImagemCapa() != null) {
            editPost.setImagemCapa(post.getImagemCapa());
        }
        
        // Salvar e retornar
        return editPost;
    }
}
