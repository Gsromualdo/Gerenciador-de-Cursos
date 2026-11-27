export interface Curso {
  id: string;
  titulo: string;
  descricao: string;
  status: boolean;
}

export interface CriarCursoRequest {
  titulo: string;
  descricao: string;
  status: boolean;
}

export interface AtualizarCursoRequest {
  titulo?: string;
  descricao?: string;
  status?: boolean;
}