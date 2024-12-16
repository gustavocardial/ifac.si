import { Component, OnInit } from '@angular/core';
import { AlertaService } from '../../service/alerta.service';
import { NavigationStart, Router } from '@angular/router';
import { Alerta } from '../../model/alerta';

@Component({
  selector: 'app-alerta',
  templateUrl: './alerta.component.html',
  styleUrl: './alerta.component.css'
})
export class AlertaComponent implements OnInit {
  alertaAtual: Alerta | null = null;
  show: boolean = false;

  constructor(
    private servico: AlertaService,
    private router: Router
    ) {}

  ngOnInit(): void {
      this.servico.receberAlerta().subscribe({
      next: (alerta: Alerta) => {
        this.exibirAlerta(alerta);
      }
    });

    this.router.events.subscribe({
      next: (evento) => {
        if (evento instanceof NavigationStart) {
          this.fecharAlerta();
        }
      }
    });
  }
    
  exibirAlerta(alerta: Alerta): void {
    this.alertaAtual = alerta;
    this.show = !this.show;
  }

  fecharAlerta(): void {
    this.show = !this.show;
    this.alertaAtual = null;
  }

  getTipoAlerta(): string {
    return this.alertaAtual ? this.alertaAtual.tipo : '';
  }

  getMensagemAlerta(): string {
    return this.alertaAtual ? this.alertaAtual.mensagem : '';
  }
  
}
