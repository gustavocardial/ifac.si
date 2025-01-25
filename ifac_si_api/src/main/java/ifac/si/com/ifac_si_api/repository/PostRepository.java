package ifac.si.com.ifac_si_api.repository;

import java.util.List;

import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;

import ifac.si.com.ifac_si_api.model.Post.Post;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface PostRepository extends JpaRepository<Post, Long>{
    @Query(
        "SELECT p FROM Post p JOIN p.usuario u JOIN p.categoria c JOIN p.tags t WHERE p.titulo LIKE %?1% OR c.nome LIKE %?1% OR CAST(p.data AS string) LIKE %?1% OR p.legenda LIKE %?1% OR u.email LIKE %?1% OR u.nomeUsuario LIKE %?1% OR t.nome LIKE %?1%"
    ) List<Post> busca(String termoBusca);
    List<Post> findByStatus(EStatus status);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.nome LIKE %:tag%")
    List<Post> findPostsByTagName(@Param("tag") String tag);

    List<Post> findByCategoriaId(Long categoriaId);

    @Query("SELECT p FROM Post p WHERE p.titulo LIKE %:termo%")
    Page<Post> busca(String termo, Pageable pageable);

    @Query("SELECT p FROM Post p")
    Page<Post> getAllPosts(Pageable pageable);


}
