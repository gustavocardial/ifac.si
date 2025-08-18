package ifac.si.com.ifac_si_api.model.Acao;

import java.time.LocalDateTime;

import ifac.si.com.ifac_si_api.model.Acao.Enum.TipoAcao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import jakarta.persistence.*;

public class Acao {
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

    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(TipoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }
}
