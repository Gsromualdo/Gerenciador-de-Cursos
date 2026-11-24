package gerenciador_cursos.sistem.domain.model;

import lombok.Data;

@Data
public class Curso {
 private CursoId id;
 private String titulo;
 private String descricao;
 private boolean status;


 public Curso(CursoId id, String titulo, String descricao, boolean status){
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
 }
    
}