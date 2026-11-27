import { Routes } from '@angular/router';

export const routes: Routes = [
    {path: '', redirectTo: 'cursos',pathMatch: 'full'},
    {
        path: 'cursos',
        loadComponent: () => import('./features/cursos/pages/cursos-lista/cursos-lista').then(
            (m) => m.CursosLista
        ),
    },
    {
        path: 'cursos/novo',
        loadComponent: () => import('./features/cursos/pages/curso-form/curso-form').then(
            (m) => m.CursoForm
        ),
    },
    {
        path: 'cursos/:id',
        loadComponent: () => import('./features/cursos/pages/curso-detalhes/curso-detalhes').then(
            (m) => m.CursoDetalhes
        ),
    },
    {
        path: 'cursos/:id/editar',
        loadComponent: () => import('./features/cursos/pages/curso-form/curso-form').then(
            (m) => m.CursoForm
        ),
    },
    { path: '**', redirectTo: 'cursos' },
];
