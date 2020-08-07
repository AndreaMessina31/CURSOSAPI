package ar.com.ada.api.cursos.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiante extends Persona {

    @Id
    @Column(name = "estudiante_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer estudianteId;

    @ManyToMany
    // primero ponemos el nombre de la Join Table "tabla intermedia" entre
    // estudiante y curso
    // luego colocacamos el nombre de la columna con la que joineamos el objeto
    // estudiante y en la
    // que estamos trabajando ahora
    // Por ultimo, el nombre de la columna con la que joineamos estudiante,
    // que se encuentra en la linea 26(curso)

    @JoinTable(name = "estudiante_x_curso", joinColumns = @JoinColumn(name = "estudiante_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursosQueAsiste= new ArrayList<>();

    // mappedBY (lo lleva el OWNER de la relacion):nombre del atributo en el objeto
    // USUARIO
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL) // nombre del atributo en el obj usuario)
    private Usuario usuario;

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public List<Curso> getCursosQueAsiste() {
        return cursosQueAsiste;
    }

    public void setCursosQueAsiste(List<Curso> cursosQueAsiste) {
        this.cursosQueAsiste = cursosQueAsiste;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setEstudiante(this);
    }

}