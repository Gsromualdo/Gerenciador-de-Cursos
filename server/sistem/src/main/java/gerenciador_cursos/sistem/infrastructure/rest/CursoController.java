package gerenciador_cursos.sistem.infrastructure.rest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciador_cursos.sistem.application.AdicionarAulaUseCase;
import gerenciador_cursos.sistem.application.AdicionarModuloUseCase;
import gerenciador_cursos.sistem.application.AtualizarAulaUseCase;
import gerenciador_cursos.sistem.application.AtualizarCursoUseCase;
import gerenciador_cursos.sistem.application.AtualizarModuloUseCase;
import gerenciador_cursos.sistem.application.BuscarCursoPorIdUseCase;
import gerenciador_cursos.sistem.application.CriarCursoUseCase;
import gerenciador_cursos.sistem.application.ExcluirAulaUseCase;
import gerenciador_cursos.sistem.application.ExcluirCursoUseCase;
import gerenciador_cursos.sistem.application.ExcluirModuloUseCase;
import gerenciador_cursos.sistem.application.ListarAulasPorModuloUseCase;
import gerenciador_cursos.sistem.application.ListarCursosUseCase;
import gerenciador_cursos.sistem.application.ListarModulosPorCursoUseCase;
import gerenciador_cursos.sistem.application.input.AdicionarAulaCommand;
import gerenciador_cursos.sistem.application.input.AdicionarModuloCommand;
import gerenciador_cursos.sistem.application.input.CriarCursoCommand;
import gerenciador_cursos.sistem.application.output.AulaOutput;
import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.application.output.ModuloOutput;
import gerenciador_cursos.sistem.domain.model.AulaId;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AdicionarAulaRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AdicionarModuloRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AtualizarAulaRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AtualizarCursoRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AtualizarModuloRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AulaResponse;
import gerenciador_cursos.sistem.infrastructure.rest.dto.CriarCursoRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.CursoResponse;
import gerenciador_cursos.sistem.infrastructure.rest.dto.ModuloResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/cursos")
public class CursoController {
        private final AtualizarAulaUseCase atualizarAulaUseCase;
        private final CriarCursoUseCase criarCursoUseCase;
        private final AdicionarModuloUseCase adicionarModuloUseCase;
        private final AdicionarAulaUseCase adicionarAulaUseCase;
        private final BuscarCursoPorIdUseCase buscarCursoPorIdUseCase;
        private final ExcluirCursoUseCase excluirCursoUseCase;
        private final ListarCursosUseCase listarCursosUseCase;
        private final ListarModulosPorCursoUseCase listarModulosPorCursoUseCase;
        private final ListarAulasPorModuloUseCase listarAulasPorModuloUseCase;
        private final ExcluirModuloUseCase excluirModuloUseCase;
        private final ExcluirAulaUseCase excluirAulaUseCase;
        private final AtualizarCursoUseCase atualizarCursoUseCase;
        private final AtualizarModuloUseCase atualizarModuloUseCase;

        public CursoController(CriarCursoUseCase criarCursoUseCase, AdicionarModuloUseCase adicionarModuloUseCase,
                        AdicionarAulaUseCase adicionarAulaUseCase, BuscarCursoPorIdUseCase buscarCursoPorIdUseCase,
                        ExcluirCursoUseCase excluirCursoUseCase, ListarCursosUseCase listarCursosUseCase,
                        ListarModulosPorCursoUseCase listarModulosPorCursoUseCase,
                        ListarAulasPorModuloUseCase listarAulasPorModuloUseCase,
                        ExcluirModuloUseCase excluirModuloUseCase,
                        ExcluirAulaUseCase excluirAulaUseCase, AtualizarCursoUseCase atualizarCursoUseCase,
                        AtualizarModuloUseCase atualizarModuloUseCase, AtualizarAulaUseCase atualizarAulaUseCase) {
                this.criarCursoUseCase = criarCursoUseCase;
                this.adicionarModuloUseCase = adicionarModuloUseCase;
                this.adicionarAulaUseCase = adicionarAulaUseCase;
                this.buscarCursoPorIdUseCase = buscarCursoPorIdUseCase;
                this.excluirCursoUseCase = excluirCursoUseCase;
                this.listarCursosUseCase = listarCursosUseCase;
                this.listarModulosPorCursoUseCase = listarModulosPorCursoUseCase;
                this.listarAulasPorModuloUseCase = listarAulasPorModuloUseCase;
                this.excluirModuloUseCase = excluirModuloUseCase;
                this.excluirAulaUseCase = excluirAulaUseCase;
                this.atualizarCursoUseCase = atualizarCursoUseCase;
                this.atualizarModuloUseCase = atualizarModuloUseCase;
                this.atualizarAulaUseCase = atualizarAulaUseCase;

        }

        @PostMapping
        public ResponseEntity<CursoResponse> criarCurso(@RequestBody CriarCursoRequest criarCursoRequest) {
                CriarCursoCommand command = new CriarCursoCommand(
                                criarCursoRequest.titulo(),
                                criarCursoRequest.descricao());

                CursoOutput output = criarCursoUseCase.execute(command);

                CursoResponse response = new CursoResponse(
                                output.id(),
                                output.titulo(),
                                output.descricao(),
                                output.status());

                return ResponseEntity
                                .created(URI.create("/cursos/" + response.id()))
                                .body(response);

        }

        @PostMapping("/{cursoId}/modulos")
        public ResponseEntity<ModuloResponse> adicionarModulo(
                        @PathVariable UUID cursoId,
                        @RequestBody AdicionarModuloRequest request) {
                AdicionarModuloCommand command = new AdicionarModuloCommand(
                                cursoId,
                                request.nomeModulo(),
                                request.ordemInicial());

                ModuloOutput output = adicionarModuloUseCase.execute(command);

                ModuloResponse response = new ModuloResponse(
                                output.id(),
                                output.nomeModulo(),
                                output.ordem());

                return ResponseEntity
                                .created(URI.create("/cursos/" + cursoId + "/modulos/" + response.id()))
                                .body(response);
        }

        @PostMapping("/{cursoId}/modulos/{moduloId}/aulas")
        public ResponseEntity<AulaResponse> criarAula(@PathVariable UUID cursoId, @PathVariable UUID moduloId,
                        @RequestBody AdicionarAulaRequest request) {
                AdicionarAulaCommand command = new AdicionarAulaCommand(
                                cursoId,
                                moduloId,
                                request.nomeAula(),
                                request.duracao(),
                                request.ordemInicial());

                AulaOutput output = adicionarAulaUseCase.execute(command);

                AulaResponse response = new AulaResponse(
                                output.id(),
                                output.nomeAula(),
                                output.duracao(),
                                output.ordem());
                return ResponseEntity
                                .created(URI.create("/cursos/" + cursoId + "/modulos/" + moduloId + "/aulas/"
                                                + response.id()))
                                .body(response);
        }

        @GetMapping("/{cursoId}")
        public ResponseEntity<CursoResponse> buscarCursoPorId(@PathVariable UUID cursoId) {
                CursoId id = new CursoId(cursoId);
                CursoOutput output = buscarCursoPorIdUseCase.execute(id);

                CursoResponse response = new CursoResponse(
                                output.id(),
                                output.titulo(),
                                output.descricao(),
                                output.status());

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{cursoId}")
        public ResponseEntity<Void> excluirCurso(@PathVariable UUID cursoId) {
                CursoId id = new CursoId(cursoId);
                excluirCursoUseCase.execute(id);

                return ResponseEntity.noContent().build();
        }

        @GetMapping()
        public ResponseEntity<List<CursoResponse>> listarTodos() {
                List<CursoResponse> responses = listarCursosUseCase.execute()
                                .stream()
                                .map(output -> new CursoResponse(
                                                output.id(),
                                                output.titulo(),
                                                output.descricao(),
                                                output.status()))
                                .toList();

                return ResponseEntity.ok(responses);
        }

        @GetMapping("/{cursoId}/modulos")
        public ResponseEntity<List<ModuloResponse>> listarModulos(@PathVariable UUID cursoId) {
                CursoId id = new CursoId(cursoId);
                List<ModuloResponse> responses = listarModulosPorCursoUseCase.execute(id)
                                .stream()
                                .map(output -> new ModuloResponse(
                                                output.id(),
                                                output.nomeModulo(),
                                                output.ordem()))
                                .toList();

                return ResponseEntity.ok(responses);
        }

        @GetMapping("/{cursoId}/modulos/{moduloId}/aulas")
        public ResponseEntity<List<AulaResponse>> listarAulas(
                        @PathVariable UUID cursoId,
                        @PathVariable UUID moduloId) {
                CursoId idCurso = new CursoId(cursoId);
                ModuloId idModulo = new ModuloId(moduloId);
                List<AulaResponse> responses = listarAulasPorModuloUseCase.execute(idCurso, idModulo)
                                .stream()
                                .map(output -> new AulaResponse(
                                                output.id(),
                                                output.nomeAula(),
                                                output.duracao(),
                                                output.ordem()))
                                .toList();

                return ResponseEntity.ok(responses);
        }

        @DeleteMapping("/{cursoId}/modulos/{moduloId}")
        public ResponseEntity<Void> excluirModulo(
                        @PathVariable UUID cursoId,
                        @PathVariable UUID moduloId) {
                CursoId idCurso = new CursoId(cursoId);
                ModuloId idModulo = new ModuloId(moduloId);
                excluirModuloUseCase.execute(idCurso, idModulo);
                return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/{cursoId}/modulos/{moduloId}/aulas/{aulaId}")
        public ResponseEntity<Void> excluirAula(
                        @PathVariable UUID cursoId,
                        @PathVariable UUID moduloId,
                        @PathVariable UUID aulaId) {
                CursoId idCurso = new CursoId(cursoId);
                ModuloId idModulo = new ModuloId(moduloId);
                AulaId idAula = new AulaId(aulaId);
                excluirAulaUseCase.execute(idCurso, idModulo, idAula);
                return ResponseEntity.noContent().build();
        }

        @PatchMapping("/{cursoId}")
        public ResponseEntity<CursoResponse> atualizarCurso(
                        @PathVariable UUID cursoId,
                        @RequestBody AtualizarCursoRequest request) {
                CursoId idCurso = new CursoId(cursoId);
                CursoOutput output = atualizarCursoUseCase.execute(
                                idCurso,
                                request.titulo(),
                                request.descricao(),
                                request.status());

                return ResponseEntity.ok(new CursoResponse(
                                output.id(),
                                output.titulo(),
                                output.descricao(),
                                output.status()));
        }

        @PatchMapping("/{cursoId}/modulos/{moduloId}")
        public ResponseEntity<ModuloResponse> atualizarModulo(
                        @PathVariable UUID cursoId,
                        @PathVariable UUID moduloId,
                        @RequestBody AtualizarModuloRequest request) {
                CursoId idCurso = new CursoId(cursoId);
                ModuloOutput output = atualizarModuloUseCase.execute(
                                idCurso,
                                moduloId,
                                request.nomeModulo(),
                                request.ordem());

                return ResponseEntity.ok(new ModuloResponse(
                                output.id(),
                                output.nomeModulo(),
                                output.ordem()));
        }

        @PatchMapping("/{cursoId}/modulos/{moduloId}/aulas/{aulaId}")
        public ResponseEntity<AulaResponse> atualizarAula(
                        @PathVariable UUID cursoId,
                        @PathVariable UUID moduloId,
                        @PathVariable UUID aulaId,
                        @RequestBody AtualizarAulaRequest request) {
                CursoId idCurso = new CursoId(cursoId);
                AulaOutput output = atualizarAulaUseCase.execute(
                                idCurso,
                                moduloId,
                                aulaId,
                                request.nomeAula(),
                                request.duracao(),
                                request.ordem());

                return ResponseEntity.ok(new AulaResponse(
                                output.id(),
                                output.nomeAula(),
                                output.duracao(),
                                output.ordem()));
        }

}
