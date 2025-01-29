package ifac.si.com.ifac_si_api.controller;

import ifac.si.com.ifac_si_api.model.JwtResponse;
import ifac.si.com.ifac_si_api.model.LoginRequest;
import ifac.si.com.ifac_si_api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNomeUsuario(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}