package ifac.si.com.ifac_si_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ifac.si.com.ifac_si_api.model.Categoria;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    @Query(
            "SELECT c FROM Categoria c WHERE c.nome LIKE %?1%"
    )List<Categoria> busca(String termoBusca);
}