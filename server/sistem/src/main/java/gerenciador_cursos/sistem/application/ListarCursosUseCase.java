package gerenciador_cursos.sistem.application;

import java.util.List;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.output.CursoOutput;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class ListarCursosUseCase {
    private final CursoRepository cursoRepository;

    public ListarCursosUseCase(CursoRepository cursoRepository){
        this.cursoRepository=cursoRepository;
    }

    public List<CursoOutput> execute() {
        return cursoRepository.ListarTodos()
        .stream()
        .map(curso -> new CursoOutput(
            curso.getId().id(),
            curso.getTitulo(),
            curso.getDescricao(),
            curso.isStatus()
        ))
        .toList();
        
    }
}
