package ar.com.ada.api.cursos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.models.request.CursoRequest;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.CursoService;

@RestController
public class CursoController {

    @Autowired
    CursoService cursoService;

    @PostMapping(name = "/api/cursos")
    public ResponseEntity<GenericResponse> crearCurso(@RequestBody CursoRequest cursoReq, Curso curso) {

        CursoService.crearCurso(curso);

        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.message = "Curso creado con éxito";
        gR.id = curso.getCursoId();
        return ResponseEntity.ok(gR);
    }

}
/*
 * Faltaba declarar el service, agregarle parametros a crearCurso (Curso curso),
 * llamar al crearCurso del Service y setear el generic response
 */