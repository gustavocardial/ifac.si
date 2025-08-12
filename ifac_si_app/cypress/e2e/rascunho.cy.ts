function loginComoAutor() {
  cy.visit('/login');
  cy.get('#nomeUsuario').type('joao.silva');
  cy.get('#senha').type('senha123');
  cy.get('button').contains('ENTRAR').click();
  cy.url().should('include', '/view_post');
}

describe('Fluxo do editor', () => {
  it('Deve acessar a área de revisão', () => {
    loginComoEditor();
    cy.visit('/revisar-posts');
    cy.contains('Revisar').should('exist');
  });
});

describe('Criação de rascunho', () => {
  it('Deve salvar rascunho de post', () => {
    loginComoAutor(); // comando customizado
    cy.visit('/criar-post');
    cy.get('#titulo').type('Post de Teste');
    cy.get('#texto').type('Conteúdo do post...');
    cy.get('button').contains('Salvar Rascunho').click();
    cy.contains('Rascunho salvo com sucesso').should('exist');
  });
});

describe('Gerenciar rascunho', () => {
  it('Deve visualizar e editar rascunho existente', () => {
    loginComoAutor();
    cy.visit('/meus-posts');
    cy.contains('Rascunho').click();
    cy.get('#texto').clear().type('Texto atualizado');
    cy.get('button').contains('Salvar').click();
    cy.contains('Post salvo com sucesso').should('exist');
  });
});