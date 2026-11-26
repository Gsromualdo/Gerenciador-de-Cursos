package gerenciador_cursos.sistem.application;
import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service
public class ExcluirModuloUseCase {

    private final CursoRepository cursoRepository;

    public ExcluirModuloUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public void execute(CursoId cursoId, ModuloId moduloId) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        curso.removerModulo(moduloId);

        cursoRepository.salvar(curso);
    }
}