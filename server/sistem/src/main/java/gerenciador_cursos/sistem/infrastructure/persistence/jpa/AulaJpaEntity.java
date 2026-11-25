package gerenciador_cursos.sistem.infrastructure.persistence.jpa;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aulas")
public class AulaJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nome_aula", nullable = false)
    private String nomeAula;

    @Column(name = "duracao", nullable = false)
    private int duracao;

    @Column(name = "ordem", nullable = false)
    private int ordem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modulo_id", nullable = false)
    private ModuloJpaEntity modulo;

    protected AulaJpaEntity() {
    }

    public AulaJpaEntity(UUID id, String nomeAula, int duracao, int ordem) {
        this.id = id;
        this.nomeAula = nomeAula;
        this.duracao = duracao;
        this.ordem = ordem;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getOrdem() {
        return ordem;
    }

    public ModuloJpaEntity getModulo() {
        return modulo;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    void setModulo(ModuloJpaEntity modulo) {
        this.modulo = modulo;
    }
}