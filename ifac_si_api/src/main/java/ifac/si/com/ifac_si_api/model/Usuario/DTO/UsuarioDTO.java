package ifac.si.com.ifac_si_api.model.Usuario.DTO;

import ifac.si.com.ifac_si_api.model.Usuario.Enum.ECargo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UsuarioDTO {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ECargo cargo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public ECargo getCargo() {
        return cargo;
    }

    public void setCargo(ECargo cargo) {
        this.cargo = cargo;
    }
}
