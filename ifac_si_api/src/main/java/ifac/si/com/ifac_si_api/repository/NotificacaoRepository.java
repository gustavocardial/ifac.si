package ifac.si.com.ifac_si_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{

    // List<Notificacao> findAllByOrderByDataHoraDesc();
    
}
