package gerenciador_cursos.sistem.application.exception;
import gerenciador_cursos.sistem.domain.model.ModuloId;

public class ModuloNaoEncontradoException extends RuntimeException{
    public ModuloNaoEncontradoException(ModuloId moduloId){
        super("Aula não encontrada com o ID: " + moduloId);
    }
}
