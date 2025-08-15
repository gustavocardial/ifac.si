package ifac.si.com.ifac_si_api.model.Usuario.DTO;

public class UsuarioResponseDTO {
    private Long id;
    private String nomeUsuario;
    private String email;
    private String cargo;
    private boolean ativo;
    
    private UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Long id, String nomeUsario, String email, String cargo, boolean ativo) {
        this.id = id;
        this.nomeUsuario = nomeUsario;
        this.email = email;
        this.cargo = cargo;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
