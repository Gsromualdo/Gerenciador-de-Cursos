package gerenciador_cursos.sistem.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Override
    public void excluir(Curso curso) {
        Objects.requireNonNull(curso, "O curso não pode ser nulo");
        Objects.requireNonNull(curso.getId(), "O ID do curso não pode ser nulo");
        Objects.requireNonNull(curso.getId().id(), "O valor do ID do curso não pode ser nulo");

        storage.remove(curso.getId());
    }

    @Override
    public List<Curso> ListarTodos() {
        return List.copyOf(storage.values());
    }

}
