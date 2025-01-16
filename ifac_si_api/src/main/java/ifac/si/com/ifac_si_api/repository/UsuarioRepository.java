package ifac.si.com.ifac_si_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ifac.si.com.ifac_si_api.model.Usuario;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(
            "SELECT p FROM Usuario p WHERE p.email LIKE %:termoBusca% " +
                    "OR p.nomeUsuario LIKE %:termoBusca% OR p.cargo = :termoBusca"
    )
    List<Usuario> busca(@Param("termoBusca") String termoBusca);


}
