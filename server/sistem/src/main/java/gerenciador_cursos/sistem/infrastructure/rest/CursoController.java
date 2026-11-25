package gerenciador_cursos.sistem.infrastructure.rest;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gerenciador_cursos.sistem.application.AdicionarAulaUseCase;
import gerenciador_cursos.sistem.application.AdicionarModuloUseCase;
import gerenciador_cursos.sistem.application.CriarCursoUseCase;
import gerenciador_cursos.sistem.application.input.AdicionarAulaCommand;
import gerenciador_cursos.sistem.application.input.AdicionarModuloCommand;
import gerenciador_cursos.sistem.application.input.CriarCursoCommand;
import gerenciador_cursos.sistem.application.output.AulaOutput;
import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.application.output.ModuloOutput;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AdicionarAulaRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AdicionarModuloRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.AulaResponse;
import gerenciador_cursos.sistem.infrastructure.rest.dto.CriarCursoRequest;
import gerenciador_cursos.sistem.infrastructure.rest.dto.CursoResponse;
import gerenciador_cursos.sistem.infrastructure.rest.dto.ModuloResponse;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    private final CriarCursoUseCase criarCursoUseCase;
    private final AdicionarModuloUseCase adicionarModuloUseCase;
    private final AdicionarAulaUseCase adicionarAulaUseCase;

    public CursoController(CriarCursoUseCase criarCursoUseCase, AdicionarModuloUseCase adicionarModuloUseCase,
            AdicionarAulaUseCase adicionarAulaUseCase) {
        this.criarCursoUseCase = criarCursoUseCase;
        this.adicionarModuloUseCase = adicionarModuloUseCase;
        this.adicionarAulaUseCase = adicionarAulaUseCase;

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
                .created(URI.create("/cursos/" + cursoId + "/modulos/" + moduloId + "/aulas/" + response.id()))
                .body(response);
    }

}
