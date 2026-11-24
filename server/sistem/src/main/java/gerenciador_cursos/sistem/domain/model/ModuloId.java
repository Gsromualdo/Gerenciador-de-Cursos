package gerenciador_cursos.sistem.domain.model;

import java.util.UUID;

public record ModuloId(UUID id) {
    public ModuloId() {
        this(UUID.randomUUID());
    }
}
