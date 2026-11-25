package gerenciador_cursos.sistem.application;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.input.CriarCursoCommand;
import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class CriarCursoUseCase {
    private final CursoRepository cursoRepository;

    public CriarCursoUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoOutput execute(CriarCursoCommand cursoCommand){
        Curso curso = new Curso(cursoCommand.titulo(), cursoCommand.descricao());
        Curso cursoSalvo = cursoRepository.salvar(curso);

        return new CursoOutput(
            cursoSalvo.getId().id(),
            cursoSalvo.getTitulo(),
            cursoSalvo.getDescricao(),
            cursoSalvo.isStatus()
        );
    }
}
