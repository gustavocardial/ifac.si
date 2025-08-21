package ifac.si.com.ifac_si_api.model.Post.DTO;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Usuario.DTO.UsuarioResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {
    private Long id;
    private String titulo;
    private UsuarioResponseDTO usuario;
    private Categoria categoria;
    private String texto;
    private LocalDateTime data;
    private String legenda;
    private String mensagemReprovacao;
    private List<Imagem> imagens;
    private Imagem imagemCapa;
    private String status;
    private String visibilidade;
    private String publicacao;
    private Long postOriginalId;

    public PostDTO(Long id, String titulo) {
    }

    public PostDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public String getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(String visibilidade) {
        this.visibilidade = visibilidade;
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

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }

    public Long getPostOriginalId() {
        return postOriginalId;
    }

    public void setPostOriginalId(Long postOriginalId) {
        this.postOriginalId = postOriginalId;
    }
}
