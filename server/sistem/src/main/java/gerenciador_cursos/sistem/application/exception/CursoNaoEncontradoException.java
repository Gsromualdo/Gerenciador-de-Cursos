package gerenciador_cursos.sistem.application.exception;
import gerenciador_cursos.sistem.domain.model.CursoId;

public class CursoNaoEncontradoException  extends RuntimeException{
    public CursoNaoEncontradoException(CursoId cursoId) {
        super("Curso não encontrado com o ID: " + cursoId);
    }
}
