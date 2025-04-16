import { ETipoAlerta } from "./enum/e-tipo-alerta";

export type Alerta = {
    tipo: ETipoAlerta;
    mensagem: string;
}