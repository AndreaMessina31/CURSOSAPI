package ar.com.ada.api.cursos.entities;

import java.util.*;

import javax.persistence.*;

import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum.TipoDocuEnum;

//anotacion para mapear a la superclase e hijos
@MappedSuperclass
public class Persona {

    private String nombre;

    @Column(name = "pais_id")
    private Integer paisId;

    @Column(name = "tipo_documento_id")
    private Integer tipoDocumentoId;
    private String documento;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PaisEnum getPaisId() {
        return PaisEnum.parse(this.paisId);
    }

    public void setPaisId(PaisEnum paisId) {
        this.paisId = paisId.getValue();
    }

    public TipoDocuEnum getTipoDocumentoId() {
        return TipoDocuEnum.parse(this.tipoDocumentoId);
    }

    public void setTipoDocumentoId(TipoDocuEnum tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId.getValue();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}