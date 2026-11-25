package gerenciador_cursos.sistem.infrastructure.rest.dto;

import java.util.UUID;

public record CursoResponse(UUID id, String titulo, String descricao, boolean status) {
    
}
