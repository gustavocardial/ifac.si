package ifac.si.com.ifac_si_api.model;

import java.time.LocalDateTime;

import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import jakarta.persistence.*;

public class Acoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime data_hora;
}
