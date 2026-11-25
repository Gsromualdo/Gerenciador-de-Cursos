package gerenciador_cursos.sistem.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;

@Repository
public interface CursoRepository {
    Curso salvar(Curso curso);
    Optional<Curso> buscarPorId(CursoId id);
}
