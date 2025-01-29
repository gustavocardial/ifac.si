package ifac.si.com.ifac_si_api.model;

public class JwtResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nomeUsuario;
    private String email;
    private String cargo;

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, Long id, String nomeUsuario, String email, String cargo) {
        this.token = token;
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.cargo = cargo;
    }

    // Getters
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

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}