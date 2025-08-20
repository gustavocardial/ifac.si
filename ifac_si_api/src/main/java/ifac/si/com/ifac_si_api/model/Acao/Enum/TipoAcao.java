package ifac.si.com.ifac_si_api.model.Acao.Enum;

public enum TipoAcao {
    EDITAR("editou um post"),
    DELETAR("deletou um post"),
    ADICIONAR("adicionou um novo post"),
    REPROVAR("reprovou um post"),
    CORRIGIR("correção de post reprovado"),
    APROVAR("aprovação de post corrigido");
    
    private String descricao;
    
    TipoAcao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}