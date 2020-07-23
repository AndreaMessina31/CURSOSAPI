package ar.com.ada.api.cursos.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.cursos.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    
}