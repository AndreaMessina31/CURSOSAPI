package ar.com.ada.api.cursos.entities;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "docente")
public class Docente extends Persona {

    @Id
    @Column(name = "docente_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docenteId;

    @ManyToMany
    // primero ponemos el nombre de la Join Table "tabla intermedia" entre docente y
    // curso
    // luego colocacamos el nombre de la columna con la que joineamos el objeto
    // docente y en la
    // que estamos trabajando ahora
    // Por ultimo, el nombre de la columna con la que joineamos docente,
    // que se encuentra en la linea 25(curso)

    @JoinTable(name = "docente_x_curso", joinColumns = @JoinColumn(name = "docente_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    @JsonIgnore
    private List<Curso> cursosQueDicta;

    @OneToOne(mappedBy = "docente")
    @JsonIgnore
    private Usuario usuario;

    public Integer getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }

    public List<Curso> getCursosQueDicta() {
        return cursosQueDicta;
    }

    public void setCursosQueDicta(List<Curso> cursosQueDicta) {
        this.cursosQueDicta = cursosQueDicta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
