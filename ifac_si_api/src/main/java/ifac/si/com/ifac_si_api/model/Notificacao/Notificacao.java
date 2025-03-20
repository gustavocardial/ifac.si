package ifac.si.com.ifac_si_api.model.Notificacao;

import java.io.Serializable;
import java.time.LocalDateTime;

import ifac.si.com.ifac_si_api.model.Notificacao.Enum.TipoAcao;
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
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;  // Usuário que realizou a ação

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;  // Post relacionado à notificação
    
    @Enumerated(EnumType.STRING)
    private TipoAcao tipoAcao;  // Enum para tipo de ação (EDITAR, DELETAR, ADICIONAR, etc.)

    private LocalDateTime dataHora;  // Data e hora da notificação
    
    private boolean lida;  // Se a notificação foi lida ou não

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

    public TipoAcao getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(TipoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
    
    // Getters e setters
}
