package ar.com.ada.api.cursos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Inscripcion;
import ar.com.ada.api.cursos.models.request.InscripcionRequest;
import ar.com.ada.api.cursos.models.response.CursoEstudianteResponse;
import ar.com.ada.api.cursos.models.response.DocenteSimplificadoResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.CursoService;
import ar.com.ada.api.cursos.services.EstudianteService;

@RestController
public class EstudianteController {
  // Declarar un service

  @Autowired
  EstudianteService estudianteService;
  @Autowired
  CursoService cursoService;


  // Post: que recibimos algo, que nos permite instanciar una Categoria y ponerle
  // datos.
  @PostMapping("/api/estudiantes")
  public ResponseEntity<GenericResponse> crearEstudiante(@RequestBody Estudiante estudiante) {

      if (estudianteService.estudianteExiste(estudiante)) {
          GenericResponse rError = new GenericResponse();
          rError.isOk = false;
          rError.message = "Este estudiante ya existe";

          return ResponseEntity.badRequest().body(rError);
      }

      estudianteService.crearEstudiante(estudiante);

      GenericResponse r = new GenericResponse();
      r.isOk = true;
      r.message = "Estudiante creada con exito";
      r.id = estudiante.getEstudianteId();

      // Aca vamos a usar O.
      // Cuando se crea, generalmetnte para los puristas, se usa el
      // CreatedAtAction(201)
      return ResponseEntity.ok(r);

  }

  @GetMapping("/api/estudiantes/{id}")
  ResponseEntity<Estudiante> buscarPorIdEstudiante(@PathVariable Integer id) {
      Estudiante estudiante = estudianteService.buscarPorId(id);
      if (estudiante == null)
          return ResponseEntity.notFound().build();

      return ResponseEntity.ok(estudiante);
  }

  // En este caso se llama al metodo buscarPorId en el DocenteService 2 veces
  // @GetMapping("/api/docentes/{id}")
  // ResponseEntity<Docente> buscarPorIdDocente(@PathVariable Integer id) {
  // if (docenteService.buscarPorId(id) == null)
  // return ResponseEntity.notFound().build();
  // return ResponseEntity.ok(docenteService.buscarPorId(id));
  // }

  @GetMapping("/api/estudiantes")
  ResponseEntity<List<Estudiante>> listarEstudiantes() {
      List<Estudiante> listaEstudiantes = estudianteService.listaEstudiantes();
      return ResponseEntity.ok(listaEstudiantes);
  }  

/*
     * - Estudiante -> Perspectiva estudiante: Ver cursos disponibles! (son todos
     * los cursos donde no este inscripto) - Estudiante -> ver mis cursos(solo
     * pueden verlo los estudiantes)
     * 
     * - /api/estudiantes/{id}/cursos?disponibles=true&categoria=1 o -
     * /api/estudiantes/{id}/cursos - /api/estudiantes/{id}/cursos/disponibles -> en
     * este caso es un metodo separado
     */
    @GetMapping("/api/estudiantes/{id}/cursos")
    public ResponseEntity<List<CursoEstudianteResponse>> listaCursos(@PathVariable Integer id,
            @RequestParam(value = "disponibles", required = false) boolean disponibles) {
        List<Curso> listaCursos = new ArrayList<>();
        Estudiante estudiante = estudianteService.buscarPorId(id);
        if (disponibles) {
            // listaCursos = algo que nos devuelva la llista de cursos disponibles.
            listaCursos = cursoService.listaCursosDisponibles(estudiante);
        } else {
            listaCursos = estudiante.getCursosQueAsiste();
        }

        List<CursoEstudianteResponse> listaSimplificada = new ArrayList<>();

        // Transformar, cada item de la lista de cursos, a un CursoEstudianteResponse
        for (Curso curso : listaCursos) {
            CursoEstudianteResponse nuevoCurso = new CursoEstudianteResponse();
            nuevoCurso.nombre = curso.getNombre();
            nuevoCurso.cursoId = curso.getCursoId();
            nuevoCurso.descripcion = curso.getDescripcion();
            nuevoCurso.categorias = curso.getCategorias();
            nuevoCurso.duracionHoras = curso.getDuracionHoras();

            for (Docente docente : curso.getDocentes()) {
                DocenteSimplificadoResponse dr = new DocenteSimplificadoResponse();
                dr.docenteId = docente.getDocenteId();
                dr.nombre = docente.getNombre();
                nuevoCurso.docentes.add(dr);
            }

            listaSimplificada.add(nuevoCurso);
        }

        return ResponseEntity.ok(listaSimplificada);

    }

    // - Estudiante -> Inscribirse a un curso(por defecto haremos que la inscripcion
    // se apruebe de una)!

    @PostMapping("/api/estudiantes/{id}/inscripciones")
    public ResponseEntity<GenericResponse> inscribir(@PathVariable Integer id, @RequestBody InscripcionRequest iR) {

        Inscripcion inscripcionCreada = estudianteService.inscribir(id, iR.cursoId);
        GenericResponse gR = new GenericResponse();
        if (inscripcionCreada == null) {
            gR.isOk = false;
            gR.message = "La inscripcion no pudo ser realizada";
            return ResponseEntity.badRequest().body(gR);
        } else {
            gR.isOk = true;
            gR.message = "La inscripcion se realizo con exito";
            gR.id = inscripcionCreada.getInscripcionId();
            return ResponseEntity.ok(gR);
        }

    }




}