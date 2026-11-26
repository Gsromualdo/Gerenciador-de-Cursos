package gerenciador_cursos.sistem.application;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class BuscarCursoPorIdUseCase {
    private final CursoRepository cursoRepository;

    public BuscarCursoPorIdUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoOutput execute(CursoId cursoId) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
        .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        return new CursoOutput(
            curso.getId().id(),
            curso.getTitulo(),
            curso.getDescricao(),
            curso.isStatus()
        );
    }
}
