package ar.com.ada.api.cursos.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inscripcion_id")
    private Integer inscripcionId;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "curso_id")
    private Curso curso;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

    @Column(name = "estado_inscripcion_id")
    private EstadoInscripcionEnum estadoInscripcionId;

    public enum EstadoInscripcionEnum {
        INACTIVO(0), ACTIVO(1);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private EstadoInscripcionEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static EstadoInscripcionEnum parse(Integer id) {
            EstadoInscripcionEnum status = null; // Default
            for (EstadoInscripcionEnum item : EstadoInscripcionEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public Integer getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(Integer inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoInscripcionEnum getEstadoInscripcionId() {
        return estadoInscripcionId;
    }

    public void setEstadoInscripcionId(EstadoInscripcionEnum estadoInscripcionId) {
        this.estadoInscripcionId = estadoInscripcionId;
    }

}