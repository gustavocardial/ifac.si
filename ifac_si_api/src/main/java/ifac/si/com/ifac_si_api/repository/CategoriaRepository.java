package ifac.si.com.ifac_si_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ifac.si.com.ifac_si_api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}