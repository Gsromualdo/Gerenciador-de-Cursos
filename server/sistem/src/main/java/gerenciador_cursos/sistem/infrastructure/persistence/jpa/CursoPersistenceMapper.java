package gerenciador_cursos.sistem.infrastructure.persistence.jpa;

import java.util.Objects;

import org.springframework.stereotype.Component;

import gerenciador_cursos.sistem.domain.model.Aula;
import gerenciador_cursos.sistem.domain.model.AulaId;
import gerenciador_cursos.sistem.domain.model.Curso;
import gerenciador_cursos.sistem.domain.model.CursoId;
import gerenciador_cursos.sistem.domain.model.Modulo;
import gerenciador_cursos.sistem.domain.model.ModuloId;

@Component
public class CursoPersistenceMapper {

    public CursoJpaEntity toEntity(Curso curso) {
        Objects.requireNonNull(curso, "O curso de domínio não pode ser nulo");

        CursoJpaEntity cursoEntity = new CursoJpaEntity(
                curso.getId().id(),
                curso.getTitulo(),
                curso.getDescricao(),
                curso.isStatus()
        );

        for (Modulo modulo : curso.getModulos()) {
            ModuloJpaEntity moduloEntity = toModuloEntity(modulo);
            cursoEntity.adicionarModulo(moduloEntity);
        }

        return cursoEntity;
    }

    public Curso toDomain(CursoJpaEntity entity) {
        Objects.requireNonNull(entity, "A entidade JPA de curso não pode ser nula");

        Curso curso = new Curso(
                new CursoId(entity.getId()),
                entity.getTitulo(),
                entity.getDescricao(),
                entity.isStatus()
        );

        for (ModuloJpaEntity moduloEntity : entity.getModulos()) {
            Modulo modulo = toModuloDomain(moduloEntity);
            curso.adicionarModulo(modulo);
        }

        return curso;
    }

    private ModuloJpaEntity toModuloEntity(Modulo modulo) {
        ModuloJpaEntity moduloEntity = new ModuloJpaEntity(
                modulo.getId().id(),
                modulo.getNomeModulo(),
                modulo.getOrdem()
        );

        for (Aula aula : modulo.getAulas()) {
            AulaJpaEntity aulaEntity = toAulaEntity(aula);
            moduloEntity.adicionarAula(aulaEntity);
        }

        return moduloEntity;
    }

    private AulaJpaEntity toAulaEntity(Aula aula) {
        return new AulaJpaEntity(
                aula.getId().id(),
                aula.getNomeAula(),
                aula.getDuracao(),
                aula.getOrdem()
        );
    }

    private Modulo toModuloDomain(ModuloJpaEntity moduloEntity) {
        Modulo modulo = new Modulo(
                new ModuloId(moduloEntity.getId()),
                moduloEntity.getNomeModulo(),
                moduloEntity.getOrdem()
        );

        for (AulaJpaEntity aulaEntity : moduloEntity.getAulas()) {
            Aula aula = toAulaDomain(aulaEntity);
            modulo.adicionarAula(aula);
        }

        return modulo;
    }

    private Aula toAulaDomain(AulaJpaEntity aulaEntity) {
        return new Aula(
                new AulaId(aulaEntity.getId()),
                aulaEntity.getNomeAula(),
                aulaEntity.getDuracao(),
                aulaEntity.getOrdem()
        );
    }
}