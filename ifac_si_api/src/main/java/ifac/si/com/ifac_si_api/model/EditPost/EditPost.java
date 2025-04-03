package ifac.si.com.ifac_si_api.model.EditPost;

import ifac.si.com.ifac_si_api.model.Post.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts_edit")
public class EditPost extends Post {
    @Column(nullable = false)
    private Long idOriginal;

    public EditPost() {}

    public EditPost(Long idOriginal) {
        this.idOriginal = idOriginal;
    }

    public Long getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(Long idOriginal) {
        this.idOriginal = idOriginal;
    }
}
