package ar.com.ada.api.cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.EstudianteService;

@RestController
public class EstudianteController {
  // Declarar un service

  @Autowired
  EstudianteService estudianteService;

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
}