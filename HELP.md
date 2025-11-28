# Consertando a data na `home.html`

- Renomeie `com.projetos.springpad.dto.PadSummaryDTO` para `com.projetos.springpad.dto.PadDTO`
- Ajuste `com.projetos.springpad.dto.PadDTO` criando o método `getCreatedAtFormatted()`
- Ajuste `src/main/resources/templates/home.html` exibindo `Em ${pad.createdAtFormatted}` no lugar da data

# Formatando `view.html` com Bootstrap

- Ajuste a tag `<main>` de `src/main/resources/templates/layouts/header.html`
- Ajuste o template `src/main/resources/templates/pad/view.html`

