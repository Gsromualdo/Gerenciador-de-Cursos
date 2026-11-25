package gerenciador_cursos.sistem.application.output;

import java.util.UUID;

public record CursoOutput(UUID id,String titulo, String descricao, boolean status) {
    
}
