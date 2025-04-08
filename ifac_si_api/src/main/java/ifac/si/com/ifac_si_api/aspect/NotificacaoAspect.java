package ifac.si.com.ifac_si_api.aspect;

import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
import ifac.si.com.ifac_si_api.model.Notificacao.Enum.TipoAcao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.service.NotificacaoService;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificacaoAspect {
     @Autowired
    private NotificacaoService notificacaoService;

    // Intercepta método que salva post
    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.salvar(..))", returning = "result")
    public void afterPostCreation(JoinPoint joinPoint, Object result) {
        if (result instanceof Post) {
            Post post = (Post) result;
            criarNotificacao(post, TipoAcao.ADICIONAR);
        }
    }

    // Intercepta método que exclui post
    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.excluir(..))")
    public void afterPostDeletion(JoinPoint joinPoint) {
        // Assumindo que o método excluir recebe o Post como parâmetro
        // Ajuste isto se seu método receber apenas o ID
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            // Se o método recebe ID do post
            if (args[0] instanceof Long) {
                Long postId = (Long) args[0];
                // Aqui você precisaria de uma referência ao PostRepository para buscar o post 
                // antes de excluí-lo, ou capturar em um pointcut @Before
                // Esta implementação depende da sua estrutura exata
            } 
            // Se o método recebe o objeto Post
            else if (args[0] instanceof Post) {
                Post post = (Post) args[0];
                criarNotificacao(post, TipoAcao.DELETAR);
            }
        }
    }

    // Método auxiliar para criar a notificação
    private void criarNotificacao(Post post, TipoAcao tipoAcao) {
        try {
            // Obter o usuário atual através do contexto de segurança
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuarioAtual = null;
            
            // Ajuste esta parte de acordo com sua implementação de segurança
            if (authentication.getPrincipal() instanceof Usuario) {
                usuarioAtual = (Usuario) authentication.getPrincipal();
            } else {
                // Lógica alternativa para obter o usuário atual
                // Por exemplo, usando um usuário de sistema para notificações automáticas
                // ou buscando por username
            }

            if (usuarioAtual != null) {
                Notificacao notificacao = new Notificacao();
                notificacao.setUsuario(usuarioAtual);
                notificacao.setPost(post);
                notificacao.setTipoAcao(tipoAcao);
                notificacao.setDataHora(LocalDateTime.now());
                notificacao.setLida(false);

                notificacaoService.criarNotificacao(notificacao);
            }
        } catch (Exception e) {
            // Log do erro
            System.err.println("Erro ao criar notificação: " + e.getMessage());
        }
    }
}
