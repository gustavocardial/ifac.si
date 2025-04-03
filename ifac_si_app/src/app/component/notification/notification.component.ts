import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {

  constructor (private router: Router) {}

  notifications = [
    {
      id: 0,
      userName: 'joao.silva',
      action: 'UPDATE',
      title: 'Introdução à Programação'
    },
    {
      id: 1,
      userName: 'maria.santos',
      action: 'DELETE',
      title: 'Dicas para Correr Melhor'
    },
    {
      id: 2,
      userName: 'pedro.oliveira',
      action: 'UPDATE',
      title: 'Destinos Incríveis para Viajar'
    },
    {
      id: 3,
      userName: 'joao.silva',
      action: 'POST',
      title: 'Conceitos Básicos de Java'
    },
    {
      id: 4,
      userName: 'maria.santos',
      action: 'UPDATE',
      title: 'Maratona de São Paulo'
    }
  ];

  alertTeste(notification: any): void {
    alert(`ID: ${notification.title}, Action: ${notification.action}`);

    if(notification.action == 'UPDATE') {
      this.router.navigate(['/administration/editor/comparation_post'], {
        queryParams: {postId: notification.id}
      })
    }
  }
}
