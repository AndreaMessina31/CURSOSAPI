package ar.com.ada.api.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.EstudianteRepository;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepo;

    public boolean crearEstudiante(Estudiante estudiante) {

        if (estudianteRepo.existsEstudiante(estudiante.getPaisId().getValue(),
                estudiante.getTipoDocumentoId().getValue(), estudiante.getDocumento())) {

            return false;
        }
        estudianteRepo.save(estudiante);
        return true;

    }

    public Estudiante crearEstudiante(String nombre, PaisEnum paisEnum, TipoDocuEnum TipoDocuEnum, String documento,
            Date fechaNacimiento) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setPaisId(paisEnum);
        estudiante.setTipoDocumentoId(TipoDocuEnum);
        estudiante.setDocumento(documento);
        estudiante.setFechaNacimiento(fechaNacimiento);
        // llamo al metodo creado en la linea 19
        boolean estudianteCreado = crearEstudiante(estudiante);
        if (estudianteCreado)
            return estudiante;
        else
            return null;

    }

    public Estudiante buscarPorId(Integer id) {
        Optional<Estudiante> opEstudiante = estudianteRepo.findById(id);

        if (opEstudiante.isPresent())
            return opEstudiante.get();
        else
            return null;

    }

    public List<Estudiante> listaEstudiantes() {
        return estudianteRepo.findAll();
    }

    public boolean estudianteExiste(Estudiante estudiante) {

        if (estudianteRepo.existsEstudiante(estudiante.getPaisId().getValue(),
                estudiante.getTipoDocumentoId().getValue(), estudiante.getDocumento()))
            return true;
        else
            return false;

    }

} 

 