import { inject, Injectable } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { LoginService } from './login.service';

export const AuthGuard: CanActivateFn = (route, state) => {

    const servicoLogin = inject(LoginService);

    if(servicoLogin.isExpired()) {
      servicoLogin.logout();
      return false;
    }

    const cargoExigido = route.data['cargo'];
    const cargoUsuario = servicoLogin. usuarioAutenticado.value.cargo;

    // Se cargoExigido for um array, verifica se o cargo do usuário está nele
    if (Array.isArray(cargoExigido)) {
      return cargoExigido.includes(cargoUsuario) || !cargoExigido;
    }

    // Comportamento original
    return cargoExigido == cargoUsuario || !cargoExigido;
  
};
