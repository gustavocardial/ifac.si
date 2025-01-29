package ifac.si.com.ifac_si_api.model;

public class AuthResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nomeUsuario;
    private String email;
    private String cargo;

    public AuthResponse(String token, Long id, String nomeUsuario, String email, String cargo) {
        this.token = token;
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.cargo = cargo;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

}