package ifac.si.com.ifac_si_api.model.Notificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import ifac.si.com.ifac_si_api.model.Acao.Acao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "notificacoes")
public class Notificacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acao_id")
    private Acao acao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private boolean lida;  // Se a notificação foi lida ou não

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

}