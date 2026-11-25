package gerenciador_cursos.sistem.infrastructure.rest.dto;

import java.util.UUID;

public record ModuloResponse(UUID id, String nomeModulo, int ordem) {
    
}
