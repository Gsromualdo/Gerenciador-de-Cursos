package gerenciador_cursos.sistem.infrastructure.rest.dto;

public record AtualizarCursoRequest(
        String titulo,
        String descricao,
        Boolean status
) {}