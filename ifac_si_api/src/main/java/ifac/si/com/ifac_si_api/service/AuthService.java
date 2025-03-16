package ifac.si.com.ifac_si_api.service;

import ifac.si.com.ifac_si_api.model.AuthRequest;
import ifac.si.com.ifac_si_api.model.AuthResponse;
import ifac.si.com.ifac_si_api.model.ECargo;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioDTO;
import ifac.si.com.ifac_si_api.model.Usuario.Usuario;
import ifac.si.com.ifac_si_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        Usuario usuario = repo.findByNomeUsuario(request.getNomeUsuario())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + request.getNomeUsuario()));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        if (!usuario.isAtivo()) {
            throw new BadCredentialsException("Usuário está inativo");
        }

        String token = jwtService.generateToken(usuario.getNomeUsuario());
        return new AuthResponse(
            token, 
            usuario.getId(), 
            usuario.getNomeUsuario(), 
            usuario.getEmail(), 
            usuario.getCargo().toString()
        );
    }

    public Usuario register(UsuarioDTO usuarioDTO) {
        if (repo.existsByNomeUsuario(usuarioDTO.getNomeUsuario())) {
            throw new RuntimeException("Nome de usuário já existe");
        }

        if (repo.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        if (usuarioDTO.getNomeUsuario() == null || usuarioDTO.getNomeUsuario().trim().isEmpty()) {
            throw new RuntimeException("Nome de usuário é obrigatório");
        }

        if (usuarioDTO.getSenha() == null || usuarioDTO.getSenha().trim().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório");
        }

        // Prepara o usuário para salvar
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(usuarioDTO.getNomeUsuario());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCargo(ECargo.valueOf(usuarioDTO.getCargo().toString()));
        usuario.setAtivo(true);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setAtivo(true);

        return repo.save(usuario);
    }

    public void deactivateUser(Long userId) {
        Usuario usuario = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(false);
        repo.save(usuario);
    }

    public void activateUser(Long userId) {
        Usuario usuario = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(true);
        repo.save(usuario);
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Usuario usuario = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(oldPassword, usuario.getSenha())) {
            throw new BadCredentialsException("Senha atual inválida");
        }

        usuario.setSenha(passwordEncoder.encode(newPassword));
        repo.save(usuario);
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
