package ar.com.ada.api.cursos.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer categoriaId;
    private String nombre;
    private String descripcion;
    @ManyToMany
    // En esta relación (ManyToMany) siempre debe existir una tabla intermedia que
    // vincule 2 tablas entre si
    // Name: nombre de la tabla intermedia
    // JoinColumn:ID de la tabla donde estamos parados
    // inverseJoinColumn:ID de la tabla con la que la relacionamos
    @JsonIgnore
    //para que no se serialice a Json
    @JoinTable(name = "curso_x_categoria", joinColumns = @JoinColumn(name = "categoria_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    // la tabla intermedia "curso x categoria", es creada por la anotacion JoinTable
    // y sus dos columnas son FK a las tablas categoria y curso
    private List<Curso> cursos= new ArrayList<>();

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}