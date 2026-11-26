package gerenciador_cursos.sistem.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import gerenciador_cursos.sistem.application.exception.CursoNaoEncontradoException;
import gerenciador_cursos.sistem.application.output.ModuloOutput;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Service
public class AtualizarModuloUseCase {

    private final CursoRepository cursoRepository;

    public AtualizarModuloUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public ModuloOutput execute(CursoId cursoId, UUID moduloId, String nomeModulo, Integer ordem) {
        Curso curso = cursoRepository.buscarPorId(cursoId)
                .orElseThrow(() -> new CursoNaoEncontradoException(cursoId));

        Modulo modulo = curso.getModulos()
                .stream()
                .filter(m -> m.getId().id().equals(moduloId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Módulo não encontrado com o ID: " + moduloId));

        if (nomeModulo != null && !nomeModulo.isBlank()) {
            modulo.setNomeModulo(nomeModulo);
        }
        if (ordem != null) {
            modulo.setOrdem(ordem);
        }

        cursoRepository.salvar(curso);
        return new ModuloOutput(
                modulo.getId().id(),
                modulo.getNomeModulo(),
                modulo.getOrdem()
        );
    }
}
