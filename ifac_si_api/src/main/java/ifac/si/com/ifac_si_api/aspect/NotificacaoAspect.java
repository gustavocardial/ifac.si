package ifac.si.com.ifac_si_api.aspect;

import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
import ifac.si.com.ifac_si_api.model.Notificacao.Enum.TipoAcao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.PostRepository;
import ifac.si.com.ifac_si_api.service.NotificacaoService;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificacaoAspect {
    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private PostRepository postRepository;
    // Intercepta método que salva post
    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.save(..))", returning = "result")
    public void afterPostCreation(JoinPoint joinPoint, Object result) {
        System.out.println("Método save interceptado!");
        if (result instanceof Post) {
            Post post = (Post) result;
            System.out.println("Post salvo com ID: " + post.getId());
            criarNotificacao(post, TipoAcao.ADICIONAR);


        } else {
            System.out.println("Resultado não é um Post: " + result.getClass().getName());
        }
    }

    // Intercepta método de exclusão ANTES de executar
    @Before("execution(* ifac.si.com.ifac_si_api.service.PostService.delete(Long)) && args(id)")
    public void beforePostDeletion(JoinPoint joinPoint, Long id) {
        System.out.println("Método delete interceptado para ID: " + id);
        try {
            Post post = postRepository.findById(id).orElse(null);
            if (post != null) {
                System.out.println("Post encontrado para exclusão: " + post.getId());
                criarNotificacao(post, TipoAcao.DELETAR);
            } else {
                System.out.println("Post não encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar notificação para exclusão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para criar a notificação
    private void criarNotificacao(Post post, TipoAcao tipoAcao) {
        
        System.out.println("Entrou aqui");
        
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
