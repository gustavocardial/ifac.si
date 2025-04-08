import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { title } from 'process';

@Component({
  selector: 'app-comparation-view',
  templateUrl: './comparation-view.component.html',
  styleUrl: './comparation-view.component.css'
})
export class ComparationViewComponent implements OnInit{
  constructor ( 
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  notification = {
    id: 0,
    userName: '',
    action: '',
    title: '',
  }

  notificationEdit = {
    id: 0,
    userName: '',
    action: '',
    title: '',
  }
  
  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('postId');

    console.log (id)

    if (id) {
      // console.log (`Id: ${id}, parseInt: ${parseInt(id, 10)}`);
      
      this.notification = this.notifications.find(noti => noti.id == parseInt(id, 10)) || this.notification;
      console.log(`Notification: ${this.notification}`);
      this.notificationEdit = this.notificationsEdit.find(noti => noti.id == parseInt(id, 10)) || this.notificationEdit;
      console.log(`NotificationEdit: ${this.notificationsEdit}`);
    }
    // if (id) {
    //   alert(`Post editado com id: ${id}`)
    // }
  }

  notificationsEdit = [
    {
      id: 0,
      userName: 'joao.silva',
      action: 'UPDATE',
      title: 'Introdução á Programação' // Erro ortográfico no "á"
    },
    {
      id: 1,
      userName: 'maria.santos',
      action: 'DELETE',
      title: 'Dicas p/ Correr Melhor' // Abreviação de "para"
    },
    {
      id: 2,
      userName: 'pedro.oliveira',
      action: 'UPDATE',
      title: 'Destinos Incriveis para Viajar' // Erro ortográfico em "Incríveis"
    },
    {
      id: 3,
      userName: 'joao.silva',
      action: 'POST',
      title: 'Conceitos Básicos de Javascript' // Erro ortográfico em "JavaScript"
    },
    {
      id: 4,
      userName: 'maria.santos',
      action: 'UPDATE',
      title: 'Maratona de São Paulo - Corra Conosco' // Adicionando um texto extra
    },
  ]

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

}
