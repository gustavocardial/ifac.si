package ifac.si.com.ifac_si_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.exception.ResourceNotFoundException;
import ifac.si.com.ifac_si_api.model.EditPost.EditPost;
import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
import ifac.si.com.ifac_si_api.model.Notificacao.Enum.TipoAcao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.NotificacaoRepository;

@Service
public class NotificacaoService {
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // Para WebSocket
    
    public Notificacao criarNotificacao(Notificacao notificacao) {
        Notificacao savedNotificacao = notificacaoRepository.save(notificacao);
        
        // Enviar notificação via WebSocket
        messagingTemplate.convertAndSend("/topic/notificacoes", savedNotificacao);
        
        return savedNotificacao;
    }
    
    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.findAllByOrderByDataHoraDesc();
    }
    
    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
            
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao notificarCriacaoPost(Post post, Usuario usuario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setPost(post);
        notificacao.setTipoAcao(TipoAcao.ADICIONAR);
        notificacao.setDataHora(LocalDateTime.now());
        notificacao.setLida(false);
        
        return criarNotificacao(notificacao);
    }
    
    public Notificacao notificarEdicaoPost(Post post, EditPost editPost, Usuario usuario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setPost(post);
        notificacao.setEditPost(editPost);
        notificacao.setTipoAcao(TipoAcao.EDITAR);
        notificacao.setDataHora(LocalDateTime.now());
        notificacao.setLida(false);
        
        return criarNotificacao(notificacao);
    }
    
    public Notificacao notificarDelecaoPost(Post post, Usuario usuario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setPost(post);
        notificacao.setTipoAcao(TipoAcao.DELETAR);
        notificacao.setDataHora(LocalDateTime.now());
        notificacao.setLida(false);
        
        return criarNotificacao(notificacao);
    }
}
