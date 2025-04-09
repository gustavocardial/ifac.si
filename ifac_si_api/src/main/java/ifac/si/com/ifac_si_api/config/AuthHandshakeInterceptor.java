package ifac.si.com.ifac_si_api.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {

        // Aqui você pode acessar headers se quiser validar o token
        // Exemplo:
        var headers = request.getHeaders();
        var authHeader = headers.getFirst("Authorization");

        System.out.println("🛡️ Token recebido no handshake: " + authHeader);

        // Adiciona o token nos atributos pra usar depois (como no ChannelInterceptor)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            attributes.put("Authorization", authHeader);
        }

        return true; // permitir o handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
    }
}
