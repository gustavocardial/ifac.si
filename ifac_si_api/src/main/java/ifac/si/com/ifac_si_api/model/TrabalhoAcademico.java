package ifac.si.com.ifac_si_api.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "trabalhos_academicos")
public class TrabalhoAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String titulo;

    private String tipo;

    private LocalDateTime data;

    private String resumo;

    private String link_pdf;
    
    @ManyToMany
    @JoinTable(
        name = "trabalho_discente", 
        joinColumns = @JoinColumn(name = "trabalho_id"),
        inverseJoinColumns = @JoinColumn(name = "discente_id")
    )
    private List<Discente> discentes;

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
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public String getResumo() {
        return resumo;
    }
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
    public String getLink_pdf() {
        return link_pdf;
    }
    public void setLink_pdf(String link_pdf) {
        this.link_pdf = link_pdf;
    }
    public List<Discente> getDiscentes() {
        return discentes;
    }
    public void setDiscentes(List<Discente> discentes) {
        this.discentes = discentes;
    }

    
}
