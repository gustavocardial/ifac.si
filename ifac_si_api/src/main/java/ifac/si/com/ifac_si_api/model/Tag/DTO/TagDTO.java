package ifac.si.com.ifac_si_api.model.Tag.DTO;

import ifac.si.com.ifac_si_api.model.Tag.Tag;

public class TagDTO {
    private Long id;
    private String nome;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.nome = tag.getNome();
    }

    public TagDTO(Long id, String nome) {
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
}

