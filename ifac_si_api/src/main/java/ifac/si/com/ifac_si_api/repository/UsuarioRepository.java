package ifac.si.com.ifac_si_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ifac.si.com.ifac_si_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Query(
        "SELECT p FROM Usuario p WHERE p.email LIKE %?1%" +
        " OR p.nomeUsuario LIKE %?1% OR p.cargo = ?1"
    ) List<Usuario> busca(String termoBusca);
}
