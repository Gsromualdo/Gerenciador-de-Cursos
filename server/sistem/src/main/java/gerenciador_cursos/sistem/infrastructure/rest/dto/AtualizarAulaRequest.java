package gerenciador_cursos.sistem.infrastructure.rest.dto;

public record AtualizarAulaRequest(
        String nomeAula,
        Integer ordem
) {}