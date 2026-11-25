package gerenciador_cursos.sistem.infrastructure.persistence.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class CursoJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "titulo", nullable = false)
    String titulo;

    @Column(name = "descricao")
    String descricao;

    @Column(name = "status",nullable = false)
    private boolean status;

    @OneToMany(
        mappedBy = "curso",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @OrderBy("ordem ASC")
    private List<ModuloJpaEntity> modulos = new ArrayList<>();

    protected CursoJpaEntity(){ }

    public CursoJpaEntity(UUID id, String titulo, String descricao, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    public void adicionarModulo(ModuloJpaEntity modulo) {
        modulos.add(modulo);
        modulo.setCurso(this);
    }

    public void limparModulos() {
        for(ModuloJpaEntity modulo: modulos){
            modulo.setCurso(null);
        }
        modulos.clear();
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public List<ModuloJpaEntity> getModulos() {
        return modulos;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
