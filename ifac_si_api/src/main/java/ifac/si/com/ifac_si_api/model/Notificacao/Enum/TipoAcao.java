package ifac.si.com.ifac_si_api.model.Notificacao.Enum;

public enum TipoAcao {
    EDITAR("editou um post"),
    DELETAR("deletou um post"),
    ADICIONAR("adicionou um novo post");
    
    private String descricao;
    
    TipoAcao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
