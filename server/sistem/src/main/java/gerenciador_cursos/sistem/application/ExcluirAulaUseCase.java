package gerenciador_cursos.sistem.application;
import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.domain.model.AulaId;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.model.ModuloId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service
public class ExcluirAulaUseCase {

    private final CursoRepository cursoRepository;

    public ExcluirAulaUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public void execute(CursoId cursoId, ModuloId moduloId, AulaId aulaId) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        Modulo modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().id().equals(moduloId.id()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado com o ID: " + moduloId));

        modulo.removerAula(aulaId);

        cursoRepository.salvar(curso);
    }
}