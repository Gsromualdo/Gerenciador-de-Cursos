export interface Aula {
  id: string;
  nomeAula: string;
  duracao: number;
  ordem: number;
}

export interface AdicionarAulaRequest {
  nomeAula: string;
  duracao: number;
  ordemInicial: number;
}

export interface AtualizarAulaRequest {
  nomeAula?: string;
  ordem?: number;
}