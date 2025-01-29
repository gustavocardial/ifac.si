package ifac.si.com.ifac_si_api.config;

import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import or

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getCargo().name())  // Assumindo que você tem um enum Role
                .build();
    }
}
