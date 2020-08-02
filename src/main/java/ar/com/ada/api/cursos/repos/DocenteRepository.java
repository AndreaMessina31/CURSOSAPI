package ar.com.ada.api.cursos.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum.TipoDocuEnum;



@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
// jpql query, se está referenciando al obj docente, conviene para prevenir sql
    // injection, los atributos son los de la clase, van con camel case
    @Query("select CASE WHEN  count(d) > 0 THEN true ELSE false END from Docente d where d.paisId=:pais and d.tipoDocumentoId=:tipoDocuEnum and d.documento=:documento")
    boolean existsDocente(Integer pais, Integer tipoDocuEnum, String documento);

    @Query("select d from Docente d where d.paisId=:pais and d.tipoDocumentoId=:tipoDocuEnum and d.documento=:documento")
    Docente buscarDocentePorDocu(Integer pais, Integer tipoDocuEnum, String documento);

}
