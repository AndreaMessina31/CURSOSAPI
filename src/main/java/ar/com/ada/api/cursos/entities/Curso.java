package ar.com.ada.api.cursos.entities;

import java.util.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.*;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_id")
    private Integer cursoId;
    private String nombre;
    // el mappedby hara referencia al ATRIBUTO que se encuentra en el objeto
    // docente= "cursos"
    // En casos de ManytoMany, OnetoOne o ManytoOne, el owner es el que lleva el
    // mappedBy!
    @ManyToMany(mappedBy = "cursosQueDicta")
    // diferenciamos cursos de docente y de estudiante!!! VER LINEA 30
    private List<Docente> docentes= new ArrayList<>();

    // el mappedby hara referencia al ATRIBUTO que se encuentra en el objeto
    // estudiante= "cursos"
    // En casos de ManytoMany, OnetoOne o ManytoOne, el owner es el que lleva el
    // mappedBy!
    @JsonIgnore
    @ManyToMany(mappedBy = "cursosQueAsiste")
    private List<Estudiante> estudiantes= new ArrayList<>();

    @Column(name = "duracion_horas")
    private Integer duracionHoras;
    private String descripcion;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Clase> clases= new ArrayList<>();
 // @JsonIgnore 
    @ManyToMany(mappedBy = "cursos")
    private List<Categoria> categorias= new ArrayList<>();

@JsonIgnore
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Inscripcion> inscripciones= new ArrayList<>();

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public void asignarDocente(Docente docente) {
        // this refiere a la instancia de la clase cursos actualmente ejecutando el
        // codigo (el metodo asignar docente)
        // docentes refiere al atributo lista de docentes que están asignados a ese
        // curso
        this.docentes.add(docente);
        // le "avisamos al docente" que tiene que dictar ese curso
        docente.getCursosQueDicta().add(this);
    }

    public void asignarEstudiante(Estudiante estudiante) {
        // this refiere a la instancia de la clase cursos actualmente ejecutando el
        // codigo(el metodo asignar estudiante)
        // estudiantes refiere al atributo lista de estudiantes que están asignados a
        // ese curso
        this.estudiantes.add(estudiante);
        // le "avisamos al estudiante" que tiene que asistir a ese curso
        estudiante.getCursosQueAsiste().add(this);
    }

    public void agregarClase(Clase clase) {
        this.clases.add(clase);
        // curso no es una lista, por lo que se setea
        clase.setCurso(this);
    }

    public void agregarCategoria(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.getCursos().add(this);
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        this.inscripciones.add(inscripcion);
        inscripcion.setCurso(this);
    }

}
