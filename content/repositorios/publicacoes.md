---
title: "Publicações"
sidebar: false
---

<style>
.post__content table {
    display: block;
    overflow-x: auto;
}
.post__content table th {
    cursor: pointer;
    user-select: none;
    white-space: nowrap;
}
.post__content table th .sort-arrow {
    display: inline-block;
    margin-left: 4px;
    font-size: 0.7em;
    opacity: 0.4;
}
.post__content table th.sort-asc .sort-arrow,
.post__content table th.sort-desc .sort-arrow {
    opacity: 1;
}
</style>

Publicações em Computação pura, aplicada e áreas correlatas por professores e alunos do Curso Superior de Tecnologia em Sistemas para Internet - IFAC.

> **Publicou?** Informe à Coordenação!

<input type="text" id="busca-publicacao" placeholder="Buscar por título, autor, tipo ou ano..." style="width: 100%; padding: 8px 12px; font-size: 14px; border: 1px solid #ccc; box-sizing: border-box;">

<script>
document.addEventListener("DOMContentLoaded", function () {
  var input = document.getElementById("busca-publicacao");
  var table = input.closest(".post__content").querySelector("table");
  var tbody = table.querySelector("tbody");
  var headers = Array.from(table.querySelectorAll("thead th, tr:first-child th"));

  // Adiciona setas nos cabeçalhos
  headers.forEach(function (th, colIndex) {
    var arrow = document.createElement("span");
    arrow.className = "sort-arrow";
    arrow.textContent = "▲";
    th.appendChild(arrow);
    th.dataset.sortDir = "";

    th.addEventListener("click", function () {
      var asc = th.dataset.sortDir !== "asc";
      headers.forEach(function (h) {
        h.dataset.sortDir = "";
        h.classList.remove("sort-asc", "sort-desc");
        h.querySelector(".sort-arrow").textContent = "▲";
      });
      th.dataset.sortDir = asc ? "asc" : "desc";
      th.classList.add(asc ? "sort-asc" : "sort-desc");
      th.querySelector(".sort-arrow").textContent = asc ? "▲" : "▼";

      var isNumeric = colIndex === 3;
      var rows = Array.from(tbody.querySelectorAll("tr"));
      rows.sort(function (a, b) {
        var aVal = a.cells[colIndex] ? a.cells[colIndex].textContent.trim() : "";
        var bVal = b.cells[colIndex] ? b.cells[colIndex].textContent.trim() : "";
        if (isNumeric) {
          return asc ? Number(aVal) - Number(bVal) : Number(bVal) - Number(aVal);
        }
        return asc ? aVal.localeCompare(bVal, "pt") : bVal.localeCompare(aVal, "pt");
      });
      rows.forEach(function (row) { tbody.appendChild(row); });
    });
  });

  // Busca
  input.addEventListener("input", function () {
    var terms = this.value.toLowerCase().split(/\s+/).filter(Boolean);
    Array.from(tbody.querySelectorAll("tr")).forEach(function (row) {
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
| **Avaliando a Importância do Arquivo contributing.md em Projetos de Código Aberto** | Silvana Gonçalves | Artigo em congresso | 2025 | [Acessar](https://sol.sbc.org.br/index.php/semish/article/view/36794) |
| **Identificação de Seis Espécies de Palmeiras Nativas na Amazônia Utilizando Redes Neurais Convolucionais e Imagens de VANT** | Airton Gaio Júnior | Artigo em congresso | 2024 | [Acessar](https://static.even3.com/anais/867303.pdf?v=638996984241248460) |
| **Evaluating pending interest table performance under the collusive interest flooding attack in named data networks** | Diego Lopes | Artigo em periódico | 2024 | [Acessar](https://doi.org/10.1007/s12243-024-01016-6) |
| **Autoria Imersiva em Realidade Virtual para Aplicações Mulsemídia Interativas** | Flávio Farias | Artigo em congresso | 2024 | [Acessar](https://sol.sbc.org.br/index.php/webmedia_estendido/article/view/30466) |
| **Development of competencies for industry 4.0: a professional training framework** | Jonas Pontes, Diego Canizio Lopes, Marlon Teixeira, Silvana Gonçalves | Artigo em revista | 2024 | [Acessar](https://dialnet.unirioja.es/servlet/articulo?codigo=10265372) |
| **Introdução ao Aprendizado de Máquina Adversarial: ataques, defesas e consequências** | Jonas Pontes | Capítulo de livro | 2024 | [Acessar](https://unihacker-club.github.io/files/unihacker_ebook_2.pdf) |
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
| **A utilização de ferramentas computacionais para a identificação de problemas de acessibilidade em espaços públicos** | José William Menezes, Marlon Teixeira | Artigo em congresso | 2019 | [Acessar](http://www.xiwticifes.ufba.br/modulos/submissao/Upload-353/86013.pdf) |
| **Experiências na adoção de Laboratórios Virtuais para o ensino de Montagem e Manutenção de Computadores** | Antonio Rege, Dirceu de Lima, Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/14312) |
| **Análise do Impacto da Ocorrência de Mudança de Conceito em Problemas de Classificação** | Jonas Pontes | Artigo em congresso | 2018 | [Acessar](https://www.researchgate.net/profile/Jonas-Pontes-3/publication/357657206_Analise_do_Impacto_da_Ocorrencia_de_Mudanca_de_Conceito_em_Problemas_de_Classificacao/links/61d84f6de669ee0f5c8f0d39/Analise-do-Impacto-da-Ocorrencia-de-Mudanca-de-Conceito-em-Problemas-de-Classificacao.pdf) |
| **Data mart construction based on semantic annotation of scientific articles**: A case study for the prioritization of drug targets | Marlon Teixeira | Artigo em periódico | 2018 | [Acessar](https://doi.org/10.1016/j.cmpb.2018.01.010) |
| **Comparando as opiniões do professor e seus alunos sobre o uso de um laboratório virtual de robótica**: um relato de experiência | Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/14346) |
| **Desenvolvimento de um Instrumento para o Registro da Experiência Docente no Uso de Laboratórios Virtuais** | Victor Vieira | Artigo em congresso | 2018 | [Acessar](https://sol.sbc.org.br/index.php/sbie/article/view/41422) |
| **A Sensitivity Approach to Energy-Efficient Mapping of Dependable Virtual Networks** | Jonas Pontes | Artigo em congresso | 2017 | [Acessar](https://ieeexplore.ieee.org/abstract/document/8031477) |
| **Assessment of TCP Parameters for Mobile Devices Concerning Performance and Energy Consumption** | Jonas Pontes | Artigo em congresso | 2017 | [Acessar](https://ieeexplore.ieee.org/abstract/document/8031464) |
| **Performance and availability modeling of hybrid storage systems** | Jonas Pontes | Artigo em congresso | 2017 | [Acessar](https://ieeexplore.ieee.org/abstract/document/8123212) |
| **QoE and energy consumption evaluation of adaptive video streaming on mobile device** | Jonas Pontes | Artigo em congresso | 2017 | [Acessar](https://ieeexplore.ieee.org/abstract/document/8016294) |
| **New Approaches for XML Data Compression** | Marlon Teixeira | Artigo em congresso | 2012 | [Acessar](https://doi.org/10.5220/0003896202330237) |

<small>* Autores então vinculados ao curso</small>
