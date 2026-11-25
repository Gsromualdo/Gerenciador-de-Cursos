package gerenciador_cursos.sistem.application;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.input.AdicionarModuloCommand;
import gerenciador_cursos.sistem.application.output.ModuloOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class AdicionarModuloUseCase {
    private final CursoRepository cursoRepository;

    public AdicionarModuloUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public ModuloOutput execute(AdicionarModuloCommand adicionarModuloCommand){
        CursoId cursoId = new CursoId(adicionarModuloCommand.cursoId());
        Curso curso = cursoRepository.buscarPorId(cursoId).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Modulo modulo = new Modulo(new ModuloId(), adicionarModuloCommand.nomeModulo(), adicionarModuloCommand.ordemInicial());

        curso.adicionarModulo(modulo);
        cursoRepository.salvar(curso);

        
        return new ModuloOutput(
            modulo.getId().id(),
            modulo.getNomeModulo(),
            modulo.getOrdem()
        );
    }
}
