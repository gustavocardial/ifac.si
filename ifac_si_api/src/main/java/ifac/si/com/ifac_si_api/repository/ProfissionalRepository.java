package ifac.si.com.ifac_si_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ifac.si.com.ifac_si_api.model.Profissional.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    
}
