package gerenciador_cursos.sistem.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

@Getter 
public class Modulo {
    private ModuloId id;
    private String nomeModulo;
    private int ordem;
    private final List<Aula> aulas = new ArrayList<>();

    public List<Aula> getAulas() {
        return Collections.unmodifiableList(aulas);
    }

    public void adicionarAula(Aula aula)  {
        if(aula == null) return ;
        aulas.add(aula);
        recalcularOrdemAulas();
    }

    public void removerAula(AulaId aulaId) {
        if(aulaId == null) {
            throw new IllegalArgumentException("O identificador da aula não pode ser nulo");
        }

        boolean removida = aulas.removeIf(aula -> Objects.equals(aula.getId(), aulaId)); // compara o aula.getid com o o id passado como parametro

        if (removida) { // se o metodo retorna true ele recalcula as aulas
            recalcularOrdemAulas();
        }
    }

    public void recalcularOrdemAulas() {
        for(int i = 0; i<aulas.size();i++){
            aulas.get(i).definirOrdem(i + 1);
        }
    }
}
