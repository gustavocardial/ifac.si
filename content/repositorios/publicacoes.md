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

Publicações de professores e alunos do Curso Superior de Tecnologia em Sistemas para Internet - IFAC.

> **Seção em construção.** Em breve, mais publicações serão adicionadas.

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
| O Perfil dos Ingressantes nos Cursos Técnicos Integrados ao Ensino Médio em Informática para Internet e Redes de Computadores | Antonio Rege, Victor Vieira | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/26387) |
| Uma Abordagem Baseada no Scrum para Melhorar a Integração entre Coordenador de Curso e Professor: Relato de Experiência no Acompanhamento das Atividades de Ensino em Disciplinas de Computação | Antonio Rege, Victor Vieira | Artigo em congresso | 2023 | [Acessar](https://sol.sbc.org.br/index.php/wie/article/view/26326) |
| Beto, o simulador de memória cache | Victor Vieira | Artigo em congresso | 2022 | [Acessar](https://sol.sbc.org.br/index.php/cbie_estendido/article/view/22603) |

<small>* Autores então vinculados ao curso</small>
