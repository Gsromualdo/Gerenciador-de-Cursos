export interface Modulo {
  id: string;
  nomeModulo: string;
  ordem: number;
}

export interface AdicionarModuloRequest {
  nomeModulo: string;
  ordemInicial: number;
}

export interface AtualizarModuloRequest {
  nomeModulo?: string;
  ordem?: number;
}