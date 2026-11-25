package gerenciador_cursos.sistem.application;


import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.input.AdicionarAulaCommand;
import gerenciador_cursos.sistem.application.output.AulaOutput;
import gerenciador_cursos.sistem.domain.model.Aula;
import gerenciador_cursos.sistem.domain.model.AulaId;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service 
public class AdicionarAulaUseCase {
    private final CursoRepository cursoRepository;

    public AdicionarAulaUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public AulaOutput execute(AdicionarAulaCommand command) {
        CursoId cursoId = new CursoId(command.cursoId());
        ModuloId moduloId = new ModuloId(command.moduloId());

        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Modulo modulo = curso.buscarModuloPorId(moduloId)
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado neste curso"));

        Aula aula = new Aula(new AulaId(), command.nomeAula(), command.duracao(), command.ordemInicial());

        modulo.adicionarAula(aula);
        cursoRepository.salvar(curso);

        return new AulaOutput(aula.getId().id(), aula.getNomeAula(), aula.getDuracao(), aula.getOrdem());

    }
}
