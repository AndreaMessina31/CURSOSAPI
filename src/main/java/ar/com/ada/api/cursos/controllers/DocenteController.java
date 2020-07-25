package ar.com.ada.api.cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.*;

@RestController
public class DocenteController {

    // Declarar un service

    @Autowired
    DocenteService docenteService;

    // Post: que recibimos algo, que nos permite instanciar una Categoria y ponerle
    // datos.
    @PostMapping("/api/docentes")
    public ResponseEntity<GenericResponse> crearDocente(@RequestBody Docente docente) {

        docenteService.crearDocente(docente);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "Docente creada con exito";
        r.id = docente.getDocenteId();

        // Aca vamos a usar Ok
        // Cuando se crea, generalmetnte para los puristas, se usa el
        // CreatedAtAction(201)
        return ResponseEntity.ok(r);

    }

    @GetMapping("/api/docentes/{id}")
    ResponseEntity<Docente> buscarPorIdDocente(@PathVariable Integer id) {
        Docente docente = docenteService.buscarPorId(id);
        if (docente == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(docente);
    }

    // En este caso se llama al metodo buscarPorId en el DocenteService 2 veces
    // @GetMapping("/api/docentes/{id}")
    // ResponseEntity<Docente> buscarPorIdDocente(@PathVariable Integer id) {
    // if (docenteService.buscarPorId(id) == null)
    // return ResponseEntity.notFound().build();
    // return ResponseEntity.ok(docenteService.buscarPorId(id));
    // }

    @GetMapping("/api/docentes")
    ResponseEntity<List<Docente>> listarDocentes() {
        List<Docente> listaDocentes = docenteService.listaDocentes();
        return ResponseEntity.ok(listaDocentes);
    }
}