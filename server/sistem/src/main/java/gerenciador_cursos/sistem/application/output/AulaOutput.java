package gerenciador_cursos.sistem.application.output;

import java.util.UUID;

public record AulaOutput(UUID id, String nomeAula, int duracao,int ordem) {
    
}
