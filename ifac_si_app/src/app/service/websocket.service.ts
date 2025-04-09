import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client, IMessage, Stomp } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: Client;

  constructor() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'), // sua rota do websocket
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('Conectado ao WebSocket!');

        // Exemplo de inscrição
        this.stompClient.subscribe('/user/notificacoes', this.onMessageReceived);
      },
      onStompError: (frame) => {
        console.error('Erro no STOMP', frame);
      },
    });

    this.stompClient.activate();
  }

  onMessageReceived(message: IMessage) {
    const body = JSON.parse(message.body);
    console.log('Nova mensagem recebida:', body);
  }

  // Se quiser enviar algo:
  sendMessage(destination: string, payload: any) {
    this.stompClient.publish({
      destination,
      body: JSON.stringify(payload),
    });
  }
}
