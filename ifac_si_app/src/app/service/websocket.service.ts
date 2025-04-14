import { Injectable, OnInit } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { Notificacao } from '../model/notificacao';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private stompClient: Client;
  private socketUrl = 'http://localhost:8080/notificacoes-websocket';
  private notificacaoSubject = new Subject<Notificacao>();
  
  constructor(private http: HttpClient) {
    const token = sessionStorage.getItem('token');
    console.log('Token:', token);

    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(this.socketUrl),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      connectHeaders: {
        Authorization: token ? `Bearer ${token}` : ''
      }
    });

    this.stompClient.onConnect = () => {
      console.log('✅ Conectado ao WebSocket!');
      
      this.stompClient.subscribe('/topic/notificacoes', (message: IMessage) => {
        console.log('📥 Mensagem recebida:', message);
        const notificacao: Notificacao = JSON.parse(message.body);
        console.log('🔔 Notificação recebida do servidor:', notificacao);
        this.notificacaoSubject.next(notificacao);
      });
      
      console.log('🔔 Inscrição no tópico /topic/notificacoes realizada.', this.notificacaoSubject);
    };
  
    this.stompClient.onStompError = (frame) => {
      console.error('❌ Erro STOMP:', frame);
    };
  }
  
  connect(): void {
    console.log('🟡 Chamou connect()');
    if (!this.stompClient.active) {
      this.stompClient.activate();
    }
  }

  subscribeToNotificacoes(): Observable<Notificacao> {
    console.log('🔔 Registrando assinatura para notificações');
    
    // Garante que a conexão está ativada
    if (!this.stompClient.active) {
      this.connect();
    }
    
    return this.notificacaoSubject.asObservable();
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
      console.log('🛑 Desconectado do WebSocket!');
    }
  }

  getNotificacoesAntigas(): Observable<Notificacao[]> {
    return this.http.get<Notificacao[]>('http://localhost:8080/notificacoes/');
  }
}
