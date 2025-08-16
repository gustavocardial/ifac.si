describe('Login de usuários', () => {
  it('Deve permitir login como autor', () => {
    cy.visit('/login');
    cy.get('#usuario').type('autor.teste');
    cy.get('#senha').type('123456');
    cy.get('button').contains('ENTRAR').click();
    cy.url().should('include', '/view_post');
  });
});