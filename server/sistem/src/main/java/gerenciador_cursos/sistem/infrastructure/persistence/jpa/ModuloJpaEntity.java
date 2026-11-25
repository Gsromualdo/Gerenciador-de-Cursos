package gerenciador_cursos.sistem.infrastructure.persistence.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class ModuloJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    UUID id;

    @Column(name = "NomeModulo", nullable = false)
    private String nomeModulo;

    @Column(name = "ordem", nullable = false)
    private int ordem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoJpaEntity curso;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("ordem ASC")
    private List<AulaJpaEntity> aulas = new ArrayList<>();

    protected ModuloJpaEntity() {
    }

    public ModuloJpaEntity(UUID id, String nomeModulo, int ordem) {
        this.id = id;
        this.nomeModulo = nomeModulo;
        this.ordem = ordem;
    }

    public void adicionarAula(AulaJpaEntity aula) {
        aulas.add(aula);
        aula.setModulo(this);
    }

    public void limparAulas() {
        for (AulaJpaEntity aula : aulas) {
            aula.setModulo(null);
        }
        aulas.clear();
    }

    public UUID getId() {
        return id;
    }

    public String getNomeModulo() {
        return nomeModulo;
    }

    public int getOrdem() {
        return ordem;
    }

    public CursoJpaEntity getCurso() {
        return curso;
    }

    public List<AulaJpaEntity> getAulas() {
        return aulas;
    }

    public void setNomeModulo(String nomeModulo) {
        this.nomeModulo = nomeModulo;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    void setCurso(CursoJpaEntity curso) {
        this.curso = curso;
    }
}