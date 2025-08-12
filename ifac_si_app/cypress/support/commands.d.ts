/// <reference types="cypress" />

declare global {
  namespace Cypress {
    interface Chainable {
      loginComoEditor(): Chainable<void>;
    }
  }
}

export {};