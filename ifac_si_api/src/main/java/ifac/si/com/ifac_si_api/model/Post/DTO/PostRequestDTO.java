package ifac.si.com.ifac_si_api.model.Post.DTO;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Tag.DTO.TagDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostRequestDTO {

    private String titulo;
    private Long usuarioId;
    private Long usuarioAlteraId;
    private Long categoriaId;
    private String texto;
    private String legenda;
    private String mensagemReprovacao;
    private String status;
    private LocalDateTime data;
    private String visibilidade;
    private String publicacao;
    private List<TagDTO> tags;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioAlteraId() {
        return usuarioAlteraId;
    }

    public void setUsuarioAlteraId(Long usuarioAlteraId) {
        this.usuarioAlteraId = usuarioAlteraId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public String getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(String publicacao) {
        this.publicacao = publicacao;
    }

    public String getMensagemReprovacao() {
        return mensagemReprovacao;
    }

    public void setMensagemReprovacao(String mensagemReprovacao) {
        this.mensagemReprovacao = mensagemReprovacao;
    }
}
