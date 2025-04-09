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
  private notificacaoObserver?: (n: Notificacao) => void;

  constructor() {
    const token = sessionStorage.getItem('token');

    console.log('Token:', token);

    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(`${this.socketUrl}?token=${token}`),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      connectHeaders: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });
  }

  connect(): void {
    this.stompClient.onConnect = () => {
      console.log('✅ Conectado ao WebSocket!');

      // Faz a inscrição no tópico
      this.stompClient.subscribe('/topic/notificacoes', (message: IMessage) => {
        const notificacao: Notificacao = JSON.parse(message.body);
        console.log('🔔 Notificação recebida do servidor:', notificacao);

        // Dispara para o observable se tiver alguém ouvindo
        if (this.notificacaoObserver) {
          this.notificacaoObserver(notificacao);
        }
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error('❌ Erro STOMP:', frame);
    };

    this.stompClient.activate();
  }

  subscribeToNotificacoes(): Observable<Notificacao> {
    return new Observable<Notificacao>(observer => {
      this.notificacaoObserver = (notificacao: Notificacao) => {
        observer.next(notificacao);
      };

      // Se o client ainda não está ativado, ativa
      if (!this.stompClient.active) {
        this.stompClient.activate();
      }
    });
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
      console.log('🛑 Desconectado do WebSocket!');
    }
  }
}
