package ar.com.ada.api.cursos.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.cursos.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByNombre(String nombre);
} 