package ifac.si.com.ifac_si_api.model;

public class AuthRequest {
    private String nomeUsuario;
    private String senha;

    // Construtores
    public AuthRequest() {}

    public AuthRequest(String nomeUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
