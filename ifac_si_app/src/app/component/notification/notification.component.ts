import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebsocketService } from '../../service/websocket.service';
import { Notificacao } from '../../model/notificacao';
import { Subscription } from 'rxjs';
import { AlertaService } from '../../service/alerta.service';
import { ETipoAlerta } from '../../model/enum/e-tipo-alerta';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent implements OnInit{

  notificacoes: Notificacao[] = Array<Notificacao>();
  private subscription: Subscription = new Subscription();

  constructor (
    private router: Router,
    private wsService: WebsocketService,
    private servicoAlerta: AlertaService,
  ) {}
  
  ngOnInit(): void {
    this.wsService.connect();


    // 🔄 Carrega as notificações antigas
    this.wsService.getNotificacoesAntigas().subscribe((antigas: Notificacao[]) => {
      console.log('📜 Notificações antigas carregadas:', antigas);
      // Coloca as antigas na lista (do mais recente pro mais antigo, se quiser inverter use .reverse())
      this.notificacoes = antigas.reverse(); 
    });

    // 🆕 Escuta novas notificações em tempo real
    this.subscription = this.wsService.subscribeToNotificacoes()
      .subscribe((notificacao: Notificacao) => {

        if (notificacao) {
          this.servicoAlerta.enviarAlerta({
            tipo: ETipoAlerta.ATENCAO,
            mensagem: "Nova notificação cadastrada no sistema"
          });
        
          console.log('📩 Nova notificação recebida no componente:', notificacao);
          this.notificacoes.unshift(notificacao);
        }
      });
    
  }

  alertTeste(notification: any): void {
    alert(`ID: ${notification.title}, Action: ${notification.action}`);

    if(notification.action == 'UPDATE') {
      this.router.navigate(['/administration/editor/comparation_post'], {
        queryParams: {postId: notification.id}
      })
    }
  }
}
