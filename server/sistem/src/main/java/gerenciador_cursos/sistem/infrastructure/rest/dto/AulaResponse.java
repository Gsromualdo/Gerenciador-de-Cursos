package gerenciador_cursos.sistem.infrastructure.rest.dto;

import java.util.UUID;

public record AulaResponse(UUID id, String nomeAula, int duracao, int ordem) {
    
}
