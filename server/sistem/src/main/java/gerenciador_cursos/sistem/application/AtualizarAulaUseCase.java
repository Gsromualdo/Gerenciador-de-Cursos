package gerenciador_cursos.sistem.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.AulaOutput;
import gerenciador_cursos.sistem.domain.model.Aula;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service
public class AtualizarAulaUseCase {

    private final CursoRepository cursoRepository;

    public AtualizarAulaUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public AulaOutput execute(
            CursoId cursoId,
            UUID moduloId,
            UUID aulaId,
            String nomeAula,
            Integer duracao,
            Integer ordem
    ) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        Modulo modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().id().equals(moduloId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado com o ID: " + moduloId));

        Aula aula = modulo.getAulas()
                .stream()
                .filter(a -> a.getId().id().equals(aulaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada com o ID: " + aulaId));

        if (nomeAula != null && !nomeAula.isBlank()) {
            aula.setNomeAula(nomeAula);
        }
        if (duracao != null) {
            aula.setDuracao(duracao);
        }
        if (ordem != null) {
            aula.setOrdem(ordem);
        }

        cursoRepository.salvar(curso);
        return new AulaOutput(
                aula.getId().id(),
                aula.getNomeAula(),
                aula.getDuracao(),
                aula.getOrdem()
        );
    }
}