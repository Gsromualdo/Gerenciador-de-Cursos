package gerenciador_cursos.sistem.application;

import java.util.List;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.AulaOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;
@Service 
public class ListarAulasPorModuloUseCase {
    private final CursoRepository cursoRepository;

    public ListarAulasPorModuloUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<AulaOutput> execute(CursoId cursoId, ModuloId moduloId) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        Modulo modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().id().equals(moduloId.id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado com o ID: " + moduloId));

        return modulo.getAulas()
                .stream()
                .map(aula -> new AulaOutput(
                        aula.getId().id(),
                        aula.getNomeAula(),
                        aula.getDuracao(),
                        aula.getOrdem()))
                .toList();
    }
}
