import { Acao } from "./acao";
import { Usuario } from "./usuario";

export type TrabalhoAcademico = {
  id: number;
  titulo: string;
  tipo: string;
  data: string;
  resumo: string;
  link_pdf: string;
};