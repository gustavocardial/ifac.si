package ifac.si.com.ifac_si_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ifac.si.com.ifac_si_api.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
    @Query(
        "SELECT t FROM Tag t WHERE t.nome LIKE %?1%"
    ) List<Tag> busca(String termoBusca);
}
