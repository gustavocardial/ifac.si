function loginComoEditor() {
  cy.visit('/login');
  cy.get('#nomeUsuario').type('maria.santos');
  cy.get('#senha').type('senha456');
  cy.get('button').contains('ENTRAR').click();
  cy.url().should('include', '/view_post');
}

// describe('Fluxo do editor', () => {
//   it('Deve acessar a área de revisão', () => {
//     loginComoEditor();
//     cy.visit('/revisar-posts');
//     cy.contains('Revisar').should('exist');
//   });
// });

describe('Notificações', () => {
  it('Deve exibir notificações após ação de edição', () => {
    loginComoEditor();
    cy.visit('administration/notificacoes/editor');
    cy.contains('editou um post').should('exist');
  });
});

describe('Fluxo de moderação', () => {
  it('Editor deve reprovar post impróprio', () => {
    loginComoEditor();
    cy.visit('/revisar-posts');
    cy.contains('NÃO AQUENTO MAIS ESSE NEGÓCIO').click();
    cy.get('button').contains('Reprovar').click();
    cy.contains('Post reprovado com sucesso').should('exist');
  });
});