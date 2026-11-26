package gerenciador_cursos.sistem.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class Curso {
   private CursoId id;
   private String titulo;
   private String descricao;
   private boolean status;
   private final List<Modulo> modulos = new ArrayList<>();

   public Curso(String titulo, String descricao) {
      this(new CursoId(), titulo, descricao, false);
   }

   public Curso(CursoId id, String titulo, String descricao, boolean status) {
      if (id == null) {
         throw new IllegalArgumentException("O identificador do curso não pode ser nulo");
      }
      if (titulo == null || titulo.isBlank()) {
         throw new IllegalArgumentException("O título do curso não pode ser vazio");
      }

      this.id = id;
      this.titulo = titulo.trim();
      this.descricao = descricao != null ? descricao.trim() : "";
      this.status = status;
   }

   public List<Modulo> getModulos() {
      return Collections.unmodifiableList(modulos);
   }

   public void adicionarModulo(Modulo modulo) {
      if (modulo == null) {
         throw new IllegalArgumentException("O módulo não pode ser nulo");
      }

      modulos.add(modulo);
      recalcularOrdemModulos();
   }

   public void removerModulo(ModuloId moduloId) {
      if (moduloId == null) {
         throw new IllegalArgumentException("O identificador do módulo não pode ser nulo");
      }

      boolean removido = modulos.removeIf(modulo -> Objects.equals(modulo.getId(), moduloId));

      if (removido) {
         recalcularOrdemModulos();
      }
   }

   public void recalcularOrdemModulos() {
      for (int i = 0; i < modulos.size(); i++) {
         modulos.get(i).definirOrdem(i + 1);
      }
   }

   public Optional<Modulo> buscarModuloPorId(ModuloId moduloId) {
      if (moduloId == null) {
         return Optional.empty();
      }

      return modulos.stream()
            .filter(modulo -> Objects.equals(modulo.getId(), moduloId))
            .findFirst();
   }
}