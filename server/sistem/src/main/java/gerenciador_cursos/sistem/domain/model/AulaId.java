package gerenciador_cursos.sistem.domain.model;

import java.util.UUID;

public record AulaId(UUID id) {
    public AulaId() {
        this(UUID.randomUUID());
    }
}
