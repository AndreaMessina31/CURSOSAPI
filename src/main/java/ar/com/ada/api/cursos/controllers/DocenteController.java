package ar.com.ada.api.cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<GenericResponse> crearDocente(@RequestBody Docente docente) {


        if (docenteService.docenteExiste(docente)) {
            GenericResponse rError = new GenericResponse();
            rError.isOk = false;
            rError.message = "Este docente ya existe";

            return ResponseEntity.badRequest().body(rError);
        }


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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF') or (hasAuthority('CLAIM_userType_DOCENTE') and hasAuthority('CLAIM_entityId_'+#id))")
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    ResponseEntity<List<Docente>> listarDocentes() {
        List<Docente> listaDocentes = docenteService.listaDocentes();
        return ResponseEntity.ok(listaDocentes);
    }
}