import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  AtualizarCursoRequest,
  CriarCursoRequest,
  Curso,
} from '../models/curso_model';
import {
  AdicionarModuloRequest,
  AtualizarModuloRequest,
  Modulo,
} from '../models/modulo_model';
import {
  AdicionarAulaRequest,
  AtualizarAulaRequest,
  Aula,
} from '../models/aula_model';

@Injectable({ providedIn: 'root' })
export class CursosApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/cursos';

  // --- Cursos ---
  listarCursos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(this.baseUrl);
  }

  buscarCursoPorId(cursoId: string): Observable<Curso> {
    return this.http.get<Curso>(`${this.baseUrl}/${cursoId}`);
  }

  criarCurso(payload: CriarCursoRequest): Observable<Curso> {
    return this.http.post<Curso>(this.baseUrl, payload);
  }

  atualizarCurso(cursoId: string, payload: AtualizarCursoRequest): Observable<Curso> {
    return this.http.patch<Curso>(`${this.baseUrl}/${cursoId}`, payload);
  }

  excluirCurso(cursoId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${cursoId}`);
  }

  // --- Módulos ---
  listarModulos(cursoId: string): Observable<Modulo[]> {
    return this.http.get<Modulo[]>(`${this.baseUrl}/${cursoId}/modulos`);
  }

  adicionarModulo(cursoId: string, payload: AdicionarModuloRequest): Observable<Modulo> {
    return this.http.post<Modulo>(`${this.baseUrl}/${cursoId}/modulos`, payload);
  }

  atualizarModulo(
    cursoId: string,
    moduloId: string,
    payload: AtualizarModuloRequest
  ): Observable<Modulo> {
    return this.http.patch<Modulo>(
      `${this.baseUrl}/${cursoId}/modulos/${moduloId}`,
      payload
    );
  }

  excluirModulo(cursoId: string, moduloId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${cursoId}/modulos/${moduloId}`);
  }

  // --- Aulas ---
  listarAulas(cursoId: string, moduloId: string): Observable<Aula[]> {
    return this.http.get<Aula[]>(
      `${this.baseUrl}/${cursoId}/modulos/${moduloId}/aulas`
    );
  }

  adicionarAula(
    cursoId: string,
    moduloId: string,
    payload: AdicionarAulaRequest
  ): Observable<Aula> {
    return this.http.post<Aula>(
      `${this.baseUrl}/${cursoId}/modulos/${moduloId}/aulas`,
      payload
    );
  }

  atualizarAula(
    cursoId: string,
    moduloId: string,
    aulaId: string,
    payload: AtualizarAulaRequest
  ): Observable<Aula> {
    return this.http.patch<Aula>(
      `${this.baseUrl}/${cursoId}/modulos/${moduloId}/aulas/${aulaId}`,
      payload
    );
  }

  excluirAula(cursoId: string, moduloId: string, aulaId: string): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/${cursoId}/modulos/${moduloId}/aulas/${aulaId}`
    );
  }
}