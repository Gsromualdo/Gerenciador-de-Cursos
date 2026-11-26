package gerenciador_cursos.sistem.application;

import java.util.List;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.ModuloOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;
@Service 
public class ListarModulosPorCursoUseCase {
    private final CursoRepository cursoRepository;

    public ListarModulosPorCursoUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<ModuloOutput> execute(CursoId cursoId) {
        Curso curso =cursoRepository.buscarPorId(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        return curso.getModulos()
        .stream()
        .map(output -> new ModuloOutput(
            output.getId().id(),
            output.getNomeModulo(),
            output.getOrdem()
        ))
        .toList();
    }
}
