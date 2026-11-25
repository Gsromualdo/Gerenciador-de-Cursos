package gerenciador_cursos.sistem.infrastructure.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;


public class InMemoryCursoRepository implements CursoRepository {

    private final Map<CursoId, Curso> storage = new ConcurrentHashMap<>();

    @Override
    public Curso salvar(Curso curso) {
        storage.put(curso.getId(), curso);
        return curso;
    }

    @Override
    public Optional<Curso> buscarPorId(CursoId id) {
        return Optional.ofNullable(storage.get(id));
    }

}
