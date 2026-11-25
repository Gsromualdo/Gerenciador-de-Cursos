package gerenciador_cursos.sistem.application.input;

import java.util.UUID;

public record AdicionarModuloCommand(
        UUID cursoId,
        String nomeModulo,
        int ordemInicial) {
}
