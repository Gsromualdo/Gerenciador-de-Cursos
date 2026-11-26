package gerenciador_cursos.sistem.application.exception;

import gerenciador_cursos.sistem.domain.model.AulaId;

public class AulaNaoEncontradaException extends RuntimeException {
    public AulaNaoEncontradaException(AulaId aulaId){
        super("Aula não encontrada com o ID: " + aulaId);
    }
}
