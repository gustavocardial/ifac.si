package ifac.si.com.ifac_si_api.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import ifac.si.com.ifac_si_api.model.Categoria;
import ifac.si.com.ifac_si_api.model.Imagem;
import ifac.si.com.ifac_si_api.model.Post.DTO.PostDTO;
import ifac.si.com.ifac_si_api.model.Tag.Tag;
import ifac.si.com.ifac_si_api.repository.CategoriaRepository;
import ifac.si.com.ifac_si_api.repository.TagRepository;

public class PostFormatter {

    @Autowired
    private static CategoriaRepository categoriaRepository;

    @Autowired
    private static TagRepository tagRepository;

    
    public static FormattedPost formatarPost(PostDTO post) {
        String html = post.getTexto();

        Document doc = Jsoup.parse(html);

        // Extrair imagens embutidas no texto
        Elements imgTags = doc.select("img");
        List<String> imagensDoTexto = new ArrayList<>();
        for (Element img : imgTags) {
            String src = img.attr("src");
            if (!src.isEmpty()) {
                imagensDoTexto.add(src);
                img.remove(); // remove do texto
            }
        }

        Categoria categoria = categoriaRepository.findById(post.getCategoria().getId())
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        String nomeCategoria = categoria.getNome();

        List<Tag> tags = tagRepository.tagsByPosts(post.getId());
        List<String> nomesTags = tags.stream()
        .map(tag -> "#" + tag.getNome().replaceAll("\\s+", "")) // remove espaços se quiser
        .collect(Collectors.toList());

        // Montar descrição com quebras de linha
        StringBuilder descricao = new StringBuilder();
        descricao.append("📌 ").append(nomeCategoria).append("\n\n");
        descricao.append("📝 ").append(post.getTitulo()).append("\n");
        descricao.append("📣 ").append(post.getLegenda()).append("\n\n");
        descricao.append(doc.body().text()).append("\n");
        descricao.append("\n🔖 Tags: ").append(String.join(" ", nomesTags));

        // Agrupar todas as imagens: embutidas + anexadas
        List<String> todasImagens = new ArrayList<>();
        if (post.getImagemCapa() != null && post.getImagemCapa().getUrl() != null) {
            todasImagens.add(post.getImagemCapa().getUrl());
        }
        if (post.getImagens() != null) {
            for (Imagem img : post.getImagens()) {
                if (img.getUrl() != null) {
                    todasImagens.add(img.getUrl());
                }
            }
        }
        todasImagens.addAll(imagensDoTexto);

        return new FormattedPost(descricao.toString(), todasImagens);
    }

    public static class FormattedPost {
        public String descricao;
        public List<String> imagens;

        public FormattedPost(String descricao, List<String> imagens) {
            this.descricao = descricao;
            this.imagens = imagens;
        }
    }
}

