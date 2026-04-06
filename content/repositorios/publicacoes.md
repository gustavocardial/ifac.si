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

<small>* Autores vinculados ao curso</small>
