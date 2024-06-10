package ifac.si.com.ifac_si_api.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    
    private String titulo;
    private Usuario usuario;
    private Categoria categoria;
    private String texto;
    private Date data;
    private String legenda;

    //Tem que colocar as notações ManytoOne, OnetoMany e tag
    
}
