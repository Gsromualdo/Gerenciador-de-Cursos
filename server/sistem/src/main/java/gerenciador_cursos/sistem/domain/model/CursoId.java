package gerenciador_cursos.sistem.domain.model;

import java.util.UUID;
public record CursoId(UUID id) {
    public CursoId() {
        this(UUID.randomUUID());
    }
}