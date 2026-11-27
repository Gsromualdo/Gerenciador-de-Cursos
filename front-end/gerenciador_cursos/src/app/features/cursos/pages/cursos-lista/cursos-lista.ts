import { Component, inject, OnInit, signal } from '@angular/core';
import { CursosApiService } from '../../../../../app/core/services/curso-api';
import { Curso } from '../../../../core/models/curso_model';
import { RouterLink } from '@angular/router';
@Component({
  imports: [RouterLink],
  selector: 'app-cursos-lista',
  styleUrl: './cursos-lista.css',
  templateUrl: './cursos-lista.html',
})
export class CursosLista implements OnInit {
  private readonly _cursoLista = inject(CursosApiService);

  readonly cursos = signal<Curso[]>([]);
  readonly carregando = signal(false);
  readonly erro = signal<String | null>(null)

  ngOnInit(): void {
    this.carregarCursos();
  }

  carregarCursos(): void {
    this.carregando.set(true);
    this.erro.set(null);

    this._cursoLista.listarCursos().subscribe({
      next: (dados) => {
        this.cursos.set(dados);
        this.carregando.set(false);
      },
      error: () => {
        this.erro.set('Não foi possível carregar os cursos. Verifique se a API está rodando.');
        this.carregando.set(false);
      },
    });
  }

  excluirCurso(cursoId: string): void {
    this._cursoLista.excluirCurso(cursoId).subscribe({
      next: () => {
        this.cursos.update((lista) => lista.filter((c) => c.id !== cursoId));
      },
      error: () => {
        this.erro.set('Erro ao excluir o curso.');
      },
    });
  }
}
