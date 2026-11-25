package gerenciador_cursos.sistem.application.input;

import java.util.UUID;

public record AdicionarAulaCommand(
        UUID cursoId,
        UUID moduloId,
        String nomeAula,
        int duracao,
        int ordemInicial) {
}
