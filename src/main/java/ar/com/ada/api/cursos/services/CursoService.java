package ar.com.ada.api.cursos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.repos.CursoRepository;

@Service
public class CursoService {
    // es un atributo de la clase CursoService
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    DocenteService docenteService;

    // 21/07 21:39 Hernán dijo que algo vamos a tener que cambiar en el resultado
    public boolean crearCurso(Curso curso) {
        if (cursoRepository.existsByNombre(curso.getNombre()))
            return false;
        cursoRepository.save(curso);
        return true;
    }

    public Curso crearCurso(String nombre, Integer categoriaId, Integer duracionHoras, String descripcion) {
        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.agregarCategoria(categoriaService.buscarPorId(categoriaId));
        curso.setDuracionHoras(duracionHoras);
        curso.setDescripcion(descripcion);
        // llamo al metodo creado en la linea 19
        boolean cursoCreado = crearCurso(curso);
        if (cursoCreado)
            return curso;
        else
            return null;

        // if (cursoRepository.existsByNombre(curso.getNombre()))
        // return null;
        // cursoRepository.save(curso);
        // return curso;

    }

    public List<Curso> listaCursos() {

        return cursoRepository.findAll();

    }

    public List<Curso> listaCursosSinDocentes() {

        List<Curso> listaCursosSinDoc = new ArrayList<>();
        for (Curso curso : listaCursos()) {
            if (curso.getDocentes().isEmpty()) {
                listaCursosSinDoc.add(curso);
            }
        }
        return listaCursosSinDoc;
    }

        public List<Curso> listaCursosDisponibles(Estudiante estudiante) {
            List<Curso> listaCursosDisponibles = new ArrayList<>();
            for (Curso curso : listaCursos()) {
                List<Estudiante> estudiantes = curso.getEstudiantes();
    
                // buscar el nro 8, levantar la mano cuando encuentro
    
                // 1
                // 3
                // 5
                // 8 <- levantar la mano
                // 7
    
                boolean anotado = false;
    
                for (Estudiante e : estudiantes) {
                    if (estudiante.getEstudianteId().equals(e.getEstudianteId())) {
                        anotado = true;
                        break; // rompe el ciclo for actual
                    }
    
                }
                if (!anotado) {
                    listaCursosDisponibles.add(curso);
                }
    
            }
            return listaCursosDisponibles;
    
        }
    

    

    public Curso buscarPorId(Integer id) {
        Optional<Curso> opCurso = cursoRepository.findById(id);

        if (opCurso.isPresent())
            return opCurso.get();
        else
            return null;

    }

    public boolean asignarDocente(Integer cursoId, Integer docenteId) {
        Curso curso = buscarPorId(cursoId);

        // Iteramos la lista de docentes que tiene el curso

        // CURSO : d1, d2, d3, d4
        // buscar el docente 3
        // primer vuelta, curso -> d1, d2, d3, d4, d3(si en el else agrego, ocurre este
        // error)
        for (Docente d : curso.getDocentes()) {
            if (d.getDocenteId().equals(docenteId))
                return false; // Si lo encuentra no hay que hacer nadas
            // Hay que terminar de recorrer.
        }

        // Asigno al docente usando el metodo asignarDocente
        // Que habiamos creado para la relacion bidireccional
        curso.asignarDocente(docenteService.buscarPorId(docenteId));

        // Actualizo el curso en la base de datos
        // dejo que el repositorio haga su magia(y cruzamos los dedos)
        cursoRepository.save(curso);

        return true;
    }

	

}