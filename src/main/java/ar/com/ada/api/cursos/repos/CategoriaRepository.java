package ar.com.ada.api.cursos.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.cursos.entities.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository <Categoria, Integer> {
    
}