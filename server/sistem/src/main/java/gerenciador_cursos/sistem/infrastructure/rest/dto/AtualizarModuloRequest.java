package gerenciador_cursos.sistem.infrastructure.rest.dto;

public record AtualizarModuloRequest(
        String nomeModulo,
        Integer ordem
) {}