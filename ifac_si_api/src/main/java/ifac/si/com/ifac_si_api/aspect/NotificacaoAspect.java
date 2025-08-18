package ifac.si.com.ifac_si_api.aspect;

import ifac.si.com.ifac_si_api.model.Acao.Acao;
import ifac.si.com.ifac_si_api.model.Acao.Enum.TipoAcao;
import ifac.si.com.ifac_si_api.model.Notificacao.Notificacao;
import ifac.si.com.ifac_si_api.model.Post.Post;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.AcaoRepository;
import ifac.si.com.ifac_si_api.repository.PostRepository;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import ifac.si.com.ifac_si_api.service.NotificacaoService;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotificacaoAspect {
    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AcaoRepository acaoRepository;
    // Intercepta método que salva post
    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.save(..))", returning = "result")
    public void afterPostCreation(JoinPoint joinPoint, Object result) {
        System.out.println("Método save interceptado!");
        if (result instanceof Post) {
            Post post = (Post) result;
            System.out.println("Post salvo com ID: " + post.getId());
            criarNotificacaoeAcao(post, TipoAcao.ADICIONAR, null);


        } else {
            System.out.println("Resultado não é um Post: " + result.getClass().getName());
        }
    }

    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.update(..))", returning = "result")
    public void afterPostUpdate(JoinPoint joinPoint, Object result) {
        System.out.println("Método save interceptado!");
        if (result instanceof Post) {
            Post post = (Post) result;
            System.out.println("Post atualizado com ID: " + post.getId());
            criarNotificacaoeAcao(post, TipoAcao.EDITAR, null);


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
                criarNotificacaoeAcao(post, TipoAcao.DELETAR, null);
            } else {
                System.out.println("Post não encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar notificação para exclusão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.reprovarPost(Long, String)) && args(postId, mensagemReprovacao)", returning = "result")
    public void afterPostReprovacao(JoinPoint joinPoint, Long postId, String mensagemReprovacao, Object result) {
        if (result instanceof Post) {
            Post post = (Post) result;
            // Notificação para o autor do post (destinatário)
            Usuario autor = post.getUsuario();
            criarNotificacaoeAcao(post, TipoAcao.REPROVAR, autor);
        }
    }

    @AfterReturning(pointcut = "execution(* ifac.si.com.ifac_si_api.service.PostService.correcaoPost(Long, ..)) && args(id, ..)", returning = "result")
    public void afterPostCorrecao(JoinPoint joinPoint, Long id, Object result) {
        if (result instanceof Post) {
            Post post = (Post) result;
            // Aqui a notificação é geral, editor vê tudo, usuário destinatário = null
            criarNotificacaoeAcao(post, TipoAcao.CORRIGIR, null);
        }
    }


    // Método auxiliar para criar a notificação
    private void criarNotificacaoeAcao(Post post, TipoAcao tipoAcao, Usuario UsuarioDestinatario) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario usuarioLogado = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        // System.out.println("Entrou aqui");
        
        try {
            Acao acao = new Acao();
            acao.setPost(post);
            acao.setTipoAcao(tipoAcao);
            acao.setUsuario(usuarioLogado);

            Notificacao notificacao = new Notificacao();
            // notificacao.setUsuario(usuarioAtual);
            if (acao != null) notificacao.setAcao(acao);
            if (UsuarioDestinatario != null) notificacao.setUsuario(UsuarioDestinatario);
            notificacao.setLida(false);

            notificacaoService.criarNotificacao(notificacao);
            System.out.println(">> Notificação criada com sucesso!" + (UsuarioDestinatario != null ? " Para: " + UsuarioDestinatario.getEmail() : " (sem destinatário específico)"));
        } catch (Exception e) {
            // Log do erro
            System.err.println("Erro ao criar notificação: " + e.getMessage());
        }
    }
}
