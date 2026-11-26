package gerenciador_cursos.sistem.application;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class ExcluirCursoUseCase {
    private final CursoRepository cursoRepository;

    public ExcluirCursoUseCase(CursoRepository cursoRepository){
        this.cursoRepository=cursoRepository;
    }

    public void execute(CursoId cursoId){
       Curso curso = cursoRepository.buscarPorId(cursoId).orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

       cursoRepository.excluir(curso);
    }
}
