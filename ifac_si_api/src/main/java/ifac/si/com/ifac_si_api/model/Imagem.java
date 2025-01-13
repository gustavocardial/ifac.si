package ifac.si.com.ifac_si_api.model;

import java.time.LocalDate;
import ifac.si.com.ifac_si_api.model.Post.Post;
import jakarta.persistence.*;

@Entity
@Table(name = "imagens")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    // URL pública gerada pelo MinIO
    @Column(nullable = false)
    private String url;

    // Nome do arquivo original
    @Column(nullable = false)
    private String nomeArquivo;

    // Tamanho do arquivo em bytes
    @Column(nullable = false)
    private Long tamanho;

    // Data do upload
    @Column(nullable = false)
    private LocalDate dataUpload;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public LocalDate getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDate dataUpload) {
        this.dataUpload = dataUpload;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
