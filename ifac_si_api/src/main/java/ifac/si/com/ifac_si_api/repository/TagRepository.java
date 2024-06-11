package ifac.si.com.ifac_si_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ifac.si.com.ifac_si_api.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
    
}
