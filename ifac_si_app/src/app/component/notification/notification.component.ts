import { Component } from '@angular/core';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  notifications = [
    {
      userName: 'joao.silva',
      action: 'UPDATE',
      title: 'Introdução à Programação'
    },
    {
      userName: 'maria.santos',
      action: 'DELETE',
      title: 'Dicas para Correr Melhor'
    },
    {
      userName: 'pedro.oliveira',
      action: 'UPDATE',
      title: 'Destinos Incríveis para Viajar'
    },
    {
      userName: 'joao.silva',
      action: 'POST',
      title: 'Conceitos Básicos de Java'
    },
    {
      userName: 'maria.santos',
      action: 'UPDATE',
      title: 'Maratona de São Paulo'
    }
  ];

  alertTeste(): void {
    alert('teste');
  }
}
