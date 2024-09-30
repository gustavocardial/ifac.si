import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  isMenuPage = false;

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Verifique se a URL atual é a que você deseja ocultar
        this.isMenuPage = this.router.url === '/login'; // ajuste '/menu' para a rota desejada
      }
    });
  }
}
