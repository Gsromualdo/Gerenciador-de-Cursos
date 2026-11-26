package gerenciador_cursos.sistem.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.repository.CursoRepository;

@Repository
public class JpaCursoRepository implements CursoRepository {
    private final SpringDataCursoRepository springDataCursoRepository;
    private final CursoPersistenceMapper cursoPersistenceMapper;

    public JpaCursoRepository(SpringDataCursoRepository springDataCursoRepository,
            CursoPersistenceMapper cursoPersistenceMapper) {
        this.springDataCursoRepository = Objects.requireNonNull(
                springDataCursoRepository,
                "O repositório Spring Data não pode ser nulo");
        this.cursoPersistenceMapper = Objects.requireNonNull(
                cursoPersistenceMapper,
                "O mapper de persistência não pode ser nulo");
    }

    @Override
    @Transactional
    public Curso salvar(Curso curso) {
        // Valida se o objeto de domínio recebido não é nulo antes de processar
        Objects.requireNonNull(curso, "O curso não pode ser nulo");

        // Converte o objeto de domínio (Curso) para a entidade do banco de dados
        // (CursoJpaEntity)
        CursoJpaEntity entity = cursoPersistenceMapper.toEntity(curso);
        // Persiste a entidade na tabela e retorna o registro salvo (com ID/dados
        // gerados pelo banco)
        CursoJpaEntity salvo = springDataCursoRepository.save(entity);
        // retorna o mapper devolta como um objeto
        return cursoPersistenceMapper.toDomain(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(CursoId id) {
        if (id == null || id.id() == null) {
            return Optional.empty();
        }

        return springDataCursoRepository.findById(id.id())
                .map(cursoPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public void excluir(Curso curso) {
        Objects.requireNonNull(curso, "O curso não pode ser nulo");
        Objects.requireNonNull(curso.getId(), "O ID do curso não pode ser nulo");
        Objects.requireNonNull(curso.getId().id(), "O valor do ID do curso não pode ser nulo");
        springDataCursoRepository.deleteById(curso.getId().id());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> ListarTodos() {
        return springDataCursoRepository.findAll()
        .stream()
        .map(cursoPersistenceMapper::toDomain)
        .toList();
    }
}