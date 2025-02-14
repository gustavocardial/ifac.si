package ifac.si.com.ifac_si_api.model.Post.DTO;

import ifac.si.com.ifac_si_api.model.Imagem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private String titulo;
    private Long usuarioId;
    private Long categoriaId;
    private String texto;
    private LocalDateTime data;
    private String legenda;
    private List<Imagem> imagens;
    private Imagem imagemCapa;
    private String status;

    public PostDTO(Long id, String titulo) {
    }

    public PostDTO() {

    }

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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    public Imagem getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(Imagem imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
