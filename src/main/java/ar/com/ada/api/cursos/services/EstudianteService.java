package ar.com.ada.api.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Inscripcion;
import ar.com.ada.api.cursos.entities.Usuario;
import ar.com.ada.api.cursos.entities.Inscripcion.EstadoInscripcionEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.EstudianteRepository;
import ar.com.ada.api.cursos.sistema.comm.EmailService;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepo;

    @Autowired
    CursoService cursoService;
    @Autowired
    EmailService emailService;

    public boolean crearEstudiante(Estudiante estudiante) {

        if (estudianteRepo.existsEstudiante(estudiante.getPaisId(), estudiante.getTipoDocumentoId().getValue(),
                estudiante.getDocumento())) {

            return false;
        }
        estudianteRepo.save(estudiante);
        return true;

    }

    public Estudiante crearEstudiante(String nombre, Integer paisEnum, TipoDocuEnum TipoDocuEnum, String documento,
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

        if (estudianteRepo.existsEstudiante(estudiante.getPaisId(), estudiante.getTipoDocumentoId().getValue(),
                estudiante.getDocumento()))
            return true;
        else
            return false;

    }

    public Inscripcion inscribir(Integer estudianteId, Integer cursoId) {
        // TODO:buscar el estudiante por Id
        // buscar el curso por Id;
        // Crear la inscripcion(aprobada por defecto)
        // Asignar la inscripcion al Usuario del Estudiante
        // Agregar el Estudiante a la Lista de Estudiantes que tiene Curso

        Estudiante estudiante = buscarPorId(estudianteId);
        Curso curso = cursoService.buscarPorId(cursoId);
        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setFecha(new Date());
        inscripcion.setEstadoInscripcionId(EstadoInscripcionEnum.ACTIVO);

        // inscripcion.setCurso(curso);
        inscripcion.setUsuario(estudiante.getUsuario());

        curso.agregarInscripcion(inscripcion);
        curso.asignarEstudiante(estudiante);

        estudianteRepo.save(estudiante);

        emailService.SendEmail(estudiante.getUsuario().getEmail(), "Curso Pinturillo: Inscripción exitosa!!!",
                "Hola " + estudiante.getUsuario().getFullname() + ", Ya estás inscripto en el curso elegido");

        return inscripcion;
    }

}
