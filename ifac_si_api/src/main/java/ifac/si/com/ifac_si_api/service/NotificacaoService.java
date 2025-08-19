package ifac.si.com.ifac_si_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import ifac.si.com.ifac_si_api.exception.ResourceNotFoundException;
import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
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

        System.out.println("🟢 Notificação enviada via WebSocket: " + savedNotificacao);

        System.out.println(savedNotificacao);
        
        return savedNotificacao;
    }
    
    public List<Notificacao> listarNotificacoes(String cargo) {
        if (!cargo.equals("EDITOR")) {
            // Se não for editor, não permitimos listagem
            throw new AccessDeniedException("Você não tem permissão para ver a lista de notificações.");
        }
        return notificacaoRepository.findAll();
    }
    
    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
            
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }
}
