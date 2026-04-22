---
title: "Publicações"
sidebar: false
---

<style>
.post__content table {
    display: block;
    overflow-x: auto;
}
</style>

Publicações em Computação pura, aplicada e áreas correlatas por professores e alunos do Curso Superior de Tecnologia em Sistemas para Internet - IFAC.

> **Publicou?** Informe à Coordenação!

<input type="text" id="busca-publicacao" placeholder="Buscar por título, autor, tipo ou ano..." style="width: 100%; padding: 8px 12px; font-size: 14px; border: 1px solid #ccc; box-sizing: border-box;">

<script>
document.addEventListener("DOMContentLoaded", function () {
  var input = document.getElementById("busca-publicacao");
  var table = input.closest(".post__content").querySelector("table");
  var rows = Array.from(table.querySelectorAll("tbody tr"));

  input.addEventListener("input", function () {
    var terms = this.value.toLowerCase().split(/\s+/).filter(Boolean);
    rows.forEach(function (row) {
      var text = row.textContent.toLowerCase();
      var match = terms.length === 0 || terms.every(function (t) { return text.indexOf(t) !== -1; });
      row.style.display = match ? "" : "none";
    });
  });
});
</script>

| Título | Autores* | Tipo | Ano | Acesso |
|--------|---------|------|-----|--------|
| **Pensamento Computacional e Soft Skills no Contexto do Ensino de Programação**: Um Mapeamento Sistemático | Antonio Rege | Artigo em congresso | 2025 | [Acessar](https://sol.sbc.org.br/index.php/sbie/article/view/38510) |
| **Identificação de Seis Espécies de Palmeiras Nativas na Amazônia Utilizando Redes Neurais Convolucionais e Imagens de VANT** | Airton Gaio Júnior | Artigo em congresso | 2024 | [Acessar](https://static.even3.com/anais/867303.pdf?v=638996984241248460) |
| **Evaluating pending interest table performance under the collusive interest flooding attack in named data networks** | Diego Lopes | Artigo em periódico | 2024 | [Acessar](https://doi.org/10.1007/s12243-024-01016-6) |
| **Autoria Imersiva em Realidade Virtual para Aplicações Mulsemídia Interativas** | Flávio Farias | Artigo em congresso | 2024 | [Acessar](https://sol.sbc.org.br/index.php/webmedia_estendido/article/view/30466) |
| **Aplicando a metodologia Design Sprint na produção de objetos educacionais** | Rodrigo Souza | Artigo em congresso | 2024 | [Acessar](https://sol.sbc.org.br/index.php/wei/article/view/29632) |
| **Identificação de Palmeiras (Arecaceae) Nativas em Áreas de floresta tropical baseado em Rede Neural Convolucional com imagens de VANT** | Airton Gaio Júnior | Artigo em periódico | 2023 | [Acessar](https://doi.org/10.26848/rbgf.v16.5.p2360-2374) |
| **O Perfil dos Ingressantes nos Cursos Técnicos Integrados ao Ensino Médio em Informática para Internet e Redes de Computadores** | Antonio Rege, Victor Vieira | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/26387) |
| **Uma Abordagem Baseada no Scrum para Melhorar a Integração entre Coordenador de Curso e Professor**: Relato de Experiência no Acompanhamento das Atividades de Ensino em Disciplinas de Computação | Antonio Rege, Victor Vieira | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/26326) |
| **Ataques de Mudança de Rótulo no Contexto da Detecção de Malwares Android**: Uma Análise Experimental | Jonas Pontes | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/sbseg/article/view/27216) |
| **Aplicando Design Sprint em Sala de Aula Invertida**: Um Estudo de Caso | Rodrigo Souza | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/sbie/article/view/26680) |
| **Verifying Open Data Portals Completeness in Compliance to a Grounding Framework** | Victor Vieira | Capítulo de livro | 2023 | [Acessar](https://doi.org/10.1007/978-3-031-41138-0_16) |
| **The Impact of the Interest Flooding Attack on the Pending Interest Table of CCN routers** | Diego Lopes | Artigo em congresso | 2022 | [Acessar](https://doi.org/10.1109/CSNet56116.2022.9955613) |
| **An Immersive Memory Game as a Cognitive Exercise for Elderly Users** | Flávio Farias | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/lique/article/view/19997) |
| **Immersive Authoring of 360 Degree Interactive Applications** | Flávio Farias | Artigo em periódico | 2022 | [Acessar](https://doi.org/10.1109/ACCESS.2022.3217799) |
| **Avaliação de Ferramentas de AutoML em Datasets de Detecção de Malwares Android** | Jonas Pontes | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/sbseg/article/view/21676) |
| **Avaliação de Métodos de Classificação baseados em Regras de Associação para Detecção de Malwares Android** | Jonas Pontes | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/sbseg/article/view/21677) |
| **Uma Análise de Métodos de Seleção de Características aplicados à Detecção de Malwares Android** | Jonas Pontes | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/sbseg/article/view/21675) |
| **Beto, o simulador de memória cache** | Victor Vieira | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/cbie_estendido/article/view/22603) |
| **AMUSEVR: A Virtual Reality Authoring Environment for Immersive Mulsemedia Applications** | Flávio Farias | Artigo em congresso | 2021 | [Acessar](https://sol.sbc.org.br/index.php/sensoryx/article/view/15687) |
| **Detecção de Malwares Android**: datasets e reprodutibilidade | Jonas Pontes | Artigo em congresso | 2021 | [Acessar](https://sol.sbc.org.br/index.php/errc/article/view/18540) |
| **Ferramentas de extração de características para análise estática de aplicativos Android** | Jonas Pontes | Artigo em congresso | 2021 | [Acessar](https://sol.sbc.org.br/index.php/errc/article/view/18539) |
| **Temporal analysis on pull request patterns: an approach with sliding window** | Silvana Gonçalves | Artigo em congresso | 2021 | [Acessar](https://doi.org/10.1145/3483899.3483906) |
| **Experiências na adoção de Laboratórios Virtuais para o ensino de Montagem e Manutenção de Computadores** | Antonio Rege, Dirceu de Lima, Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/14312) |
| **Comparando as opiniões do professor e seus alunos sobre o uso de um laboratório virtual de robótica**: um relato de experiência | Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/14346) |
| **Desenvolvimento de um Instrumento para o Registro da Experiência Docente no Uso de Laboratórios Virtuais** | Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/sbie/article/view/41422) |

<small>* Autores então vinculados ao curso</small>
