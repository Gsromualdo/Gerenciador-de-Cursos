import { Component, OnInit, inject, signal } from '@angular/core';
import {
  FormArray,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { forkJoin, of, switchMap } from 'rxjs';
import { CursosApiService } from '../../../../../app/core/services/curso-api';

interface AulaFormValue {
  nomeAula: string;
  duracao: number,
  ordemInicial: number;
}

interface ModuloFormValue {
  nomeModulo: string;
  ordemInicial: number;
  aulas: AulaFormValue[];
}

@Component({
  selector: 'app-curso-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './curso-form.html',
  styleUrl: './curso-form.css',
})
export class CursoForm implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly cursoApi = inject(CursosApiService);

  readonly cursoId = signal<string | null>(null);
  readonly modoEdicao = signal(false);
  readonly salvando = signal(false);
  readonly erro = signal<string | null>(null);

  readonly cursoForm = new FormGroup({
    titulo: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(3)],
    }),
    descricao: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(10)],
    }),
    status: new FormControl(true, { nonNullable: true }),
    modulos: new FormArray([this.criarModuloGroup(1)], [Validators.required]),
  });

  get modulosArray(): FormArray {
    return this.cursoForm.get('modulos') as FormArray;
  }

  getAulasArray(moduloIndex: number): FormArray {
    return this.modulosArray.at(moduloIndex).get('aulas') as FormArray;
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cursoId.set(id);
      this.modoEdicao.set(true);
      this.modulosArray.clear();
      this.modulosArray.clearValidators();
      this.modulosArray.updateValueAndValidity();
      this.carregarCursoParaEdicao(id);
    }
  }

  criarAulaGroup(ordem = 1): FormGroup {
    return new FormGroup({
      nomeAula: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(3)],
      }),
      duracao: new FormControl(10, {
        nonNullable:true,
        validators: [Validators.required,Validators.min(1)],
      }),
      ordemInicial: new FormControl(ordem, {
        nonNullable: true,
        validators: [Validators.required, Validators.min(1)],
      }),
    });
  }

  criarModuloGroup(ordem = 1): FormGroup {
    return new FormGroup({
      nomeModulo: new FormControl('', {
        nonNullable: true,
        validators: [Validators.required, Validators.minLength(3)],
      }),
      ordemInicial: new FormControl(ordem, {
        nonNullable: true,
        validators: [Validators.required, Validators.min(1)],
      }),
      aulas: new FormArray([this.criarAulaGroup(1)], [Validators.required]),
    });
  }

  adicionarModulo(): void {
    const proximaOrdem = this.modulosArray.length + 1;
    this.modulosArray.push(this.criarModuloGroup(proximaOrdem));
  }

  removerModulo(index: number): void {
    if (this.modulosArray.length > 1) {
      this.modulosArray.removeAt(index);
    }
  }

  adicionarAula(moduloIndex: number): void {
    const aulas = this.getAulasArray(moduloIndex);
    const proximaOrdem = aulas.length + 1;
    aulas.push(this.criarAulaGroup(proximaOrdem));
  }

  removerAula(moduloIndex: number, aulaIndex: number): void {
    const aulas = this.getAulasArray(moduloIndex);
    if (aulas.length > 1) {
      aulas.removeAt(aulaIndex);
    }
  }

  resetarFormulario(): void {
    this.erro.set(null);
    this.cursoForm.reset({
      titulo: '',
      descricao: '',
      status: true,
    });
    this.modulosArray.clear();

    if (!this.modoEdicao()) {
      this.modulosArray.push(this.criarModuloGroup(1));
    }
  }

  carregarCursoParaEdicao(id: string): void {
    this.cursoApi.buscarCursoPorId(id).subscribe({
      next: (curso) => {
        this.cursoForm.patchValue({
          titulo: curso.titulo,
          descricao: curso.descricao,
          status: curso.status,
        });
      },
      error: () => {
        this.erro.set('Não foi possível carregar os dados do curso para edição.');
      },
    });
  }

  salvar(): void {
    if (this.cursoForm.invalid) {
      this.cursoForm.markAllAsTouched();
      return;
    }

    this.salvando.set(true);
    this.erro.set(null);

    const { titulo, descricao, status, modulos } = this.cursoForm.getRawValue();
    const idAtual = this.cursoId();

    if (this.modoEdicao() && idAtual) {
      this.cursoApi.atualizarCurso(idAtual, { titulo, descricao, status }).subscribe({
        next: () => {
          this.salvando.set(false);
          this.router.navigate(['/cursos', idAtual]);
        },
        error: () => {
          this.salvando.set(false);
          this.erro.set('Erro ao atualizar o curso.');
        },
      });
      return;
    }

    this.cursoApi
      .criarCurso({ titulo, descricao, status })
      .pipe(
        switchMap((cursoCriado) => {
          const listaModulos = modulos as ModuloFormValue[];
          if (!listaModulos.length) {
            return of(cursoCriado);
          }

          const requisicoesModulos = listaModulos.map((mod) =>
            this.cursoApi
              .adicionarModulo(cursoCriado.id, {
                nomeModulo: mod.nomeModulo,
                ordemInicial: mod.ordemInicial,
              })
              .pipe(
                switchMap((moduloCriado) => {
                  const requisicoesAulas = mod.aulas.map((aula) =>
                    this.cursoApi.adicionarAula(cursoCriado.id, moduloCriado.id, {
                      nomeAula: aula.nomeAula,
                      duracao: aula.duracao,
                      ordemInicial: aula.ordemInicial
                    })
                  );
                  return requisicoesAulas.length ? forkJoin(requisicoesAulas) : of([]);
                })
              )
          );

          return forkJoin(requisicoesModulos).pipe(switchMap(() => of(cursoCriado)));
        })
      )
      .subscribe({
        next: (cursoCriado) => {
          this.salvando.set(false);
          this.router.navigate(['/cursos', cursoCriado.id]);
        },
        error: () => {
          this.salvando.set(false);
          this.erro.set('Erro ao salvar o curso, módulos ou aulas.');
        },
      });
  }
}