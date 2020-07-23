package ar.com.ada.api.cursos.entities;

import java.util.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;
    private String username;
    private String password;
    private String email;

    @Column(name = "fecha_login")
    // las anotaciones ColumnName solo se utilizan en tipo de datos:STRING, INTEGER
    // o INT
    // ENUM o DATE, nunca sobre OBJETOS (casos objetos Docente y Estudiante)
    private Date fechaLogin;

    // ver linea 32
    @Column(name = "tipo_usuario_id")
    private TipoUsuarioEnum tipoUsuarioId;

    @OneToOne
    @JoinColumn(name = "docente_id", referencedColumnName = "docente_id")
    private Docente docente;

    @OneToOne
    @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    private Estudiante estudiante;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Inscripcion> inscripciones;

    // "tipoUsuarioId" es una tabla con un enumerado y se representa de esta forma
    public enum TipoUsuarioEnum {
        DOCENTE(1), ESTUDIANTE(2), STAFF(3);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private TipoUsuarioEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoUsuarioEnum parse(Integer id) {
            TipoUsuarioEnum status = null; // Default
            for (TipoUsuarioEnum item : TipoUsuarioEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    public TipoUsuarioEnum getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuarioEnum tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

}
