package gerenciador_cursos.sistem.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class Aula {
    private AulaId id;
    private String nomeAula;
    private int duracao;
    private int ordem;

    public Aula(AulaId id, String nomeAula, int duracao, int ordem) {
        if (id == null) {
            throw new IllegalArgumentException("O identificador da aula não pode ser nulo");
        }
        if (nomeAula == null || nomeAula.isBlank()) {
            throw new IllegalArgumentException("O nome da aula não pode ser vazio");
        }
        if (duracao < 1) {
            throw new IllegalArgumentException("A duração da aula deve ser de pelo menos 1 minuto");
        }

        this.id = id;
        this.nomeAula = nomeAula.trim();
        this.duracao = duracao;
        definirOrdem(ordem);
    }

    public void definirOrdem(int ordem) {
        if (ordem < 1) {
            throw new IllegalArgumentException("A ordem deve ser no mínimo 1");
        }

        this.ordem = ordem;
    }
}