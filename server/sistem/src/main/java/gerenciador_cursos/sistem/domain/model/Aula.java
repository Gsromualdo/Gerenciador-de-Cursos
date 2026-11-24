package gerenciador_cursos.sistem.domain.model;

import lombok.Getter;


@Getter 
public class Aula {
    private AulaId id;
    private String nomeAula;
    private int duracao;
    private int ordem;

    public void definirOrdem(int ordem) {
        if(ordem < 1) {
            throw new  IllegalArgumentException("A ordem deve ser no mínimo 1");
        } 

        this.ordem =ordem;
    }
}
