import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import { Notificacao } from '../model/notificacao';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: Client;

  private socketUrl = 'http://localhost:8080/notificacoes-websocket';

  constructor() {
    const token = sessionStorage.getItem('token'); // ou onde você armazena o JWT

    console.log ('Token: ',token)

    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(this.socketUrl),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      connectHeaders: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });
  }

  connect(): void {
    this.stompClient.onConnect = () => {
      console.log('Conectado ao WebSocket!');
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Erro STOMP:', frame);
    };

    this.stompClient.activate();
  }

  subscribeToNotificacoes(): Observable<Notificacao> {
    return new Observable<Notificacao>(observer => {
      this.stompClient.onConnect = () => {
        this.stompClient.subscribe('/topic/notificacoes', (message: IMessage) => {
          const notificacao: Notificacao = JSON.parse(message.body);
          observer.next(notificacao);
        });
      };

      // Garante ativação se ainda não estiver
      if (!this.stompClient.active) {
        this.stompClient.activate();
      }
    });
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
      console.log('Desconectado do WebSocket!');
    }
  }
}
