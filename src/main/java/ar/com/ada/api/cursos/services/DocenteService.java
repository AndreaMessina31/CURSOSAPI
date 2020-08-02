package ar.com.ada.api.cursos.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.*;

@Service
public class DocenteService {
    // es un atributo de la clase CursoService
    @Autowired
    DocenteRepository docenteRepository;

    public boolean crearDocente(Docente docente) {
        if (docenteRepository.existsDocente(docente.getPaisId().getValue(), docente.getTipoDocumentoId().getValue(),
                docente.getDocumento()))
            return false;
        docenteRepository.save(docente);
        return true;
    }

    public Docente crearDocente(String nombre, PaisEnum paisEnum, TipoDocuEnum TipoDocuEnum, String documento,
            Date fechaNacimiento) {
        Docente docente = new Docente();
        docente.setNombre(nombre);
        docente.setPaisId(paisEnum);
        docente.setTipoDocumentoId(TipoDocuEnum);
        docente.setDocumento(documento);
        docente.setFechaNacimiento(fechaNacimiento);
        // llamo al metodo creado en la linea 19
        boolean docenteCreado = crearDocente(docente);
        if (docenteCreado)
            return docente;
        else
            return null;

        // if (DocenteRepository.existsByNombre(Docente.getNombre()))
        // return null;
        // DocenteRepository.save(Docente);
        // return Docente;

    }

    public Docente buscarPorId(Integer id) {
        Optional<Docente> opDocente = docenteRepository.findById(id);

        if (opDocente.isPresent())
            return opDocente.get();
        else
            return null;

    }

    public List<Docente> listaDocentes() {
        return docenteRepository.findAll();
    }

    public boolean docenteExiste(Docente docente) {

        if (docenteRepository.existsDocente(docente.getPaisId().getValue(), docente.getTipoDocumentoId().getValue(),
                docente.getDocumento()))
            return true;
        else
            return false;

    }

}