package gerenciador_cursos.sistem.application;
import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service
public class AtualizarCursoUseCase {

    private final CursoRepository cursoRepository;

    public AtualizarCursoUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoOutput execute(CursoId cursoId, String titulo, String descricao, Boolean status) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        if (titulo != null && !titulo.isBlank()) {
            curso.setTitulo(titulo);
        }
        if (descricao != null) {
            curso.setDescricao(descricao);
        }
        if (status != null) {
            curso.setStatus(status);
        }

        Curso salvo = cursoRepository.salvar(curso);
        return new CursoOutput(
                salvo.getId().id(),
                salvo.getTitulo(),
                salvo.getDescricao(),
                salvo.isStatus()
        );
    }
}
