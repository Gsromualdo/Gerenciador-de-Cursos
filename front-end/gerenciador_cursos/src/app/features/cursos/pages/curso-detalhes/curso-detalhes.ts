import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CursosApiService } from '../../../../../app/core/services/curso-api';
import { Curso } from '../../../../../app/core/models/curso_model';
import { Modulo } from '../../../../../app/core/models/modulo_model';
import { Aula } from '../../../../../app/core/models/aula_model';

interface FormModuloState {
  nomeModulo: string;
  ordem: number;
}

interface FormAulaState {
  nomeAula: string;
  duracao: number;
  ordem: number;
}

@Component({
  imports: [RouterLink],
  selector: 'app-curso-detalhes',
  styleUrl: './curso-detalhes.css',
  templateUrl: './curso-detalhes.html',
})
export class CursoDetalhes implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly cursoApi = inject(CursosApiService);

  readonly cursoId = signal<string>('');
  readonly curso = signal<Curso | null>(null);
  readonly modulos = signal<Modulo[]>([]);
  readonly moduloExpandidoId = signal<string | null>(null);
  readonly aulasPorModulo = signal<Record<string, Aula[]>>({});

  readonly carregando = signal(false);
  readonly erro = signal<string | null>(null);
  readonly excluindoId = signal<string | null>(null);
  readonly salvandoItem = signal(false);

  readonly editandoModuloId = signal<string | null>(null);
  readonly formModulo = signal<FormModuloState>({
    nomeModulo: '',
    ordem: 1,
  });

  readonly editandoAulaId = signal<string | null>(null);
  readonly formAula = signal<FormAulaState>({
    nomeAula: '',
    duracao: 10,
    ordem: 1,
  });

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.erro.set('ID do curso não informado.');
      return;
    }

    this.cursoId.set(id);
    this.carregandoDetalhes(id);
  }

  carregandoDetalhes(id: string): void {
    this.carregando.set(true);
    this.erro.set(null);

    this.cursoApi.buscarCursoPorId(id).subscribe({
      next: (dadosCurso) => {
        this.curso.set(dadosCurso);
        this.carregando.set(false);
      },
      error: () => {
        this.erro.set('Não foi possível carregar os detalhes do curso.');
        this.carregando.set(false);
      },
    });

    this.cursoApi.listarModulos(id).subscribe({
      next: (listaModulos) => {
        this.modulos.set(listaModulos);
      },
      error: () => {
        this.erro.set('Não foi possível carregar os módulos do curso.');
      },
    });
  }

  alternarModulo(moduloId: string): void {
    const atual = this.moduloExpandidoId();
    if (atual === moduloId) {
      this.moduloExpandidoId.set(null);
      return;
    }

    this.moduloExpandidoId.set(moduloId);

    if (!this.aulasPorModulo()[moduloId]) {
      this.cursoApi.listarAulas(this.cursoId(), moduloId).subscribe({
        next: (aulas) => {
          this.aulasPorModulo.update((mapa) => ({
            ...mapa,
            [moduloId]: aulas,
          }));
        },
        error: () => {
          this.erro.set('Não foi possível carregar as aulas do módulo.');
        },
      });
    }
  }

  // --- Curso ---
  excluirCurso(): void {
    const id = this.cursoId();
    if (!id || this.excluindoId()) return;

    this.excluindoId.set(id);
    this.erro.set(null);

    this.cursoApi.excluirCurso(id).subscribe({
      next: () => {
        this.excluindoId.set(null);
        this.router.navigate(['/cursos']);
      },
      error: () => {
        this.excluindoId.set(null);
        this.erro.set('Erro ao excluir o curso.');
      },
    });
  }

  // --- Módulos ---
  iniciarEdicaoModulo(modulo: Modulo): void {
    this.erro.set(null);
    this.editandoAulaId.set(null);
    this.editandoModuloId.set(modulo.id);
    this.formModulo.set({
      nomeModulo: modulo.nomeModulo,
      ordem: modulo.ordem,
    });
  }

  cancelarEdicaoModulo(): void {
    this.editandoModuloId.set(null);
  }

  atualizarCampoModulo(campo: keyof FormModuloState, valor: string): void {
    const atual = this.formModulo();
    if (campo === 'nomeModulo') {
      this.formModulo.set({ ...atual, nomeModulo: valor });
      return;
    }

    const numero = Number(valor);
    this.formModulo.set({
      ...atual,
      ordem: Number.isFinite(numero) && numero >= 1 ? numero : 1,
    });
  }

  salvarEdicaoModulo(moduloId: string): void {
    const id = this.cursoId();
    if (!id || this.salvandoItem()) return;

    const dados = this.formModulo();
    const nomeLimpo = dados.nomeModulo.trim();
    if (!nomeLimpo) {
      this.erro.set('Informe o nome do módulo.');
      return;
    }

    this.salvandoItem.set(true);
    this.erro.set(null);

    const payload = {
      nomeModulo: nomeLimpo,
      ordem: dados.ordem,
    };

    this.cursoApi.atualizarModulo(id, moduloId, payload).subscribe({
      next: (moduloAtualizado) => {
        this.modulos.update((lista) =>
          lista.map((m) =>
            m.id === moduloId ? { ...m, ...payload, ...moduloAtualizado } : m
          )
        );
        this.editandoModuloId.set(null);
        this.salvandoItem.set(false);
      },
      error: () => {
        this.salvandoItem.set(false);
        this.erro.set('Erro ao atualizar o módulo.');
      },
    });
  }

  excluirModulo(moduloId: string): void {
    const id = this.cursoId();
    if (!id || this.excluindoId()) return;

    this.excluindoId.set(moduloId);
    this.erro.set(null);

    this.cursoApi.excluirModulo(id, moduloId).subscribe({
      next: () => {
        this.modulos.update((lista) => lista.filter((m) => m.id !== moduloId));
        this.aulasPorModulo.update((mapa) => {
          const copia = { ...mapa };
          delete copia[moduloId];
          return copia;
        });
        if (this.editandoModuloId() === moduloId) {
          this.editandoModuloId.set(null);
        }
        if (this.moduloExpandidoId() === moduloId) {
          this.moduloExpandidoId.set(null);
        }
        this.excluindoId.set(null);
      },
      error: () => {
        this.excluindoId.set(null);
        this.erro.set('Erro ao excluir o módulo.');
      },
    });
  }

  // --- Aulas ---
  iniciarEdicaoAula(aula: Aula): void {
    this.erro.set(null);
    this.editandoModuloId.set(null);
    this.editandoAulaId.set(aula.id);
    this.formAula.set({
      nomeAula: aula.nomeAula,
      duracao: aula.duracao ?? 10,
      ordem: aula.ordem,
    });
  }

  cancelarEdicaoAula(): void {
    this.editandoAulaId.set(null);
  }

  atualizarCampoAula(campo: keyof FormAulaState, valor: string): void {
    const atual = this.formAula();
    if (campo === 'nomeAula') {
      this.formAula.set({ ...atual, nomeAula: valor });
      return;
    }

    const numero = Number(valor);
    if (campo === 'duracao') {
      this.formAula.set({
        ...atual,
        duracao: Number.isFinite(numero) && numero >= 1 ? numero : 1,
      });
      return;
    }

    this.formAula.set({
      ...atual,
      ordem: Number.isFinite(numero) && numero >= 1 ? numero : 1,
    });
  }

  salvarEdicaoAula(moduloId: string, aulaId: string): void {
    const id = this.cursoId();
    if (!id || this.salvandoItem()) return;

    const dados = this.formAula();
    const nomeLimpo = dados.nomeAula.trim();
    if (!nomeLimpo) {
      this.erro.set('Informe o nome da aula.');
      return;
    }

    this.salvandoItem.set(true);
    this.erro.set(null);

    const payload = {
      nomeAula: nomeLimpo,
      duracao: dados.duracao,
      ordem: dados.ordem,
    };

    this.cursoApi.atualizarAula(id, moduloId, aulaId, payload).subscribe({
      next: (aulaAtualizada) => {
        this.aulasPorModulo.update((mapa) => ({
          ...mapa,
          [moduloId]: (mapa[moduloId] ?? []).map((a) =>
            a.id === aulaId ? { ...a, ...payload, ...aulaAtualizada } : a
          ),
        }));
        this.editandoAulaId.set(null);
        this.salvandoItem.set(false);
      },
      error: () => {
        this.salvandoItem.set(false);
        this.erro.set('Erro ao atualizar a aula.');
      },
    });
  }

  excluirAula(moduloId: string, aulaId: string): void {
    const id = this.cursoId();
    if (!id || this.excluindoId()) return;

    this.excluindoId.set(aulaId);
    this.erro.set(null);

    this.cursoApi.excluirAula(id, moduloId, aulaId).subscribe({
      next: () => {
        this.aulasPorModulo.update((mapa) => ({
          ...mapa,
          [moduloId]: (mapa[moduloId] ?? []).filter((a) => a.id !== aulaId),
        }));
        if (this.editandoAulaId() === aulaId) {
          this.editandoAulaId.set(null);
        }
        this.excluindoId.set(null);
      },
      error: () => {
        this.excluindoId.set(null);
        this.erro.set('Erro ao excluir a aula.');
      },
    });
  }
}