package ifac.si.com.ifac_si_api.model.Post;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ifac.si.com.ifac_si_api.model.*;
import ifac.si.com.ifac_si_api.model.Post.Enum.EStatus;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import jakarta.persistence.*;

@Entity
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "posts")
public class Post implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String titulo;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "categoria_id", nullable = true)
    private Categoria categoria;

    @ManyToMany
    @JsonManagedReference
    @JsonIgnoreProperties("posts")
    private List<Tag> tags;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private LocalDateTime data;

    private String legenda;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens;

    @JoinColumn(name = "imagem_capa_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Imagem imagemCapa;

    @Enumerated(EnumType.STRING)
    private EStatus status;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public void removeImagem(Imagem img) {
        // Verifica se a lista de imagens existe e se a imagem está na lista
        if (this.imagens != null && this.imagens.contains(img)) {
            // Remove a imagem da lista
            this.imagens.remove(img);
            // Limpa a referência do post na imagem, o que mantém a relação bidirecional
            img.setPost(null);
        }
    }

    public void addImagem(Imagem img) {
        // Verifica se a lista de imagens existe
        if (this.imagens == null) {
            // Se não existir, cria uma nova lista
            this.imagens = new ArrayList<>();
        }
        // Adiciona a imagem na lista de imagens
        this.imagens.add(img);
        // Estabelece a relação bidirecional com o post
        img.setPost(this);
    }

    public Imagem getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(Imagem imagemCapa) {
        this.imagemCapa = imagemCapa;
    }


    //Testar relacionamentos e engenharia reserva no workbench
}