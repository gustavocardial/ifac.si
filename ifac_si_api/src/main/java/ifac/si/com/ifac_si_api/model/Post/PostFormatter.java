package ifac.si.com.ifac_si_api.model.Post;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PostFormatter {
    
    public static FormattedPost formatarPost(Post post) {
        String html = post.getTexto(); // texto com tags HTML

        Document doc = Jsoup.parse(html);

        // Extrair imagens
        Elements imgTags = doc.select("img");
        List<String> imagens = new ArrayList<>();
        for (Element img : imgTags) {
            String src = img.attr("src");
            if (!src.isEmpty()) {
                imagens.add(src);
                img.remove(); // remove do texto
            }
        }

        // Montar descrição limpa
        StringBuilder descricao = new StringBuilder();
        descricao.append("<h4>").append(post.getCategoria().getNome()).append("</h4>");
        descricao.append("<h1>").append(post.getTitulo()).append("</h1>");
        descricao.append("<h2>").append(post.getLegenda()).append("</h2>");
        descricao.append("<p>").append(doc.body().html()).append("</p>");

        return new FormattedPost(descricao.toString(), imagens);
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

