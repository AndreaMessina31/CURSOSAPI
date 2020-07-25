package ar.com.ada.api.cursos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.models.request.CategoriaModifRequest;
import ar.com.ada.api.cursos.models.response.CategoriaResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.*;

@RestController
public class CategoriaController {

    // Declarar un service

    @Autowired
    CategoriaService categoriaService;

    // Post: que recibimos algo, que nos permite instanciar una Categoria y ponerle
    // datos.
    @PostMapping("/api/categorias")
    public ResponseEntity<GenericResponse> crearCategoria(@RequestBody Categoria categoria) {

        categoriaService.crearCategoria(categoria);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "Categoria Creada con exito";
        r.id = categoria.getCategoriaId();

        // Aca vamos a usar Ok
        // Cuando se crea, generalmetnte para los puristas, se usa el
        // CreatedAtAction(201)
        return ResponseEntity.ok(r);

    }

    @PutMapping(("/api/categorias/{id}"))
    ResponseEntity<GenericResponse> actualizarCategoriaPorId(@PathVariable Integer id,
            @RequestBody CategoriaModifRequest cMR) {
        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        categoria.setNombre(cMR.nombre);
        categoria.setDescripcion(cMR.descripcion);
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(categoria);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "Categoria actualizada con éxito";
        r.id = categoriaActualizada.getCategoriaId();

        return ResponseEntity.ok(r);
    }

    @GetMapping("/api/categorias/{id}")
    ResponseEntity<CategoriaResponse> buscarPorIdCategoria(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarPorId(id);

        CategoriaResponse cGR = new CategoriaResponse();
        cGR.nombre = categoria.getNombre();
        cGR.descripcion = categoria.getDescripcion();

        return ResponseEntity.ok(cGR);
    }
    // se cambia el web method GET /api/categorias
    // para que traiga una lista de categorias(y no categoriaResponse)

    @GetMapping("/api/categorias")
    ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> listaCategorias = categoriaService.listarTodas();

        /*
         * List<CategoriaResponse> listaCategoriasResponse = new
         * ArrayList<CategoriaResponse>(); for (Categoria c : listaCategorias) {
         * CategoriaResponse cR = new CategoriaResponse(); cR.nombre = c.getNombre();
         * cR.descripcion = c.getDescripcion(); listaCategoriasResponse.add(cR); }
         * return ResponseEntity.ok(listaCategoriasResponse);
         */
        return ResponseEntity.ok(listaCategorias);
    }

}

/*
 * Creado por Flor
 * 
 * @PutMapping(("/categorias/{id}")) ResponseEntity<GenericResponse>
 * actualizarCategoriaPorId(@PathVariable Integer id,
 * 
 * @RequestBody CategoriaModifRequest cMR) { Categoria categoria =
 * categoriaService.buscarPorId(id); if (categoria == null) { return
 * ResponseEntity.notFound().build(); }
 * 
 * categoria.setNombre(cMR.nombre); categoria.setDescripcion(cMR.descripcion);
 * Categoria categoriaActualizada =
 * categoriaService.actualizarCategoria(categoria);
 * 
 * GenericResponse r = new GenericResponse(); r.isOk = true; r.message =
 * "Categoria actualizada con éxito"; r.id =
 * categoriaActualizada.getCategoriaId();
 * 
 * return ResponseEntity.ok(r); }
 * 
 * @GetMapping("/categorias/{id}") ResponseEntity<CategoriaResponse>
 * buscarPorIdCategoria(@PathVariable Integer id) { Categoria categoria =
 * categoriaService.buscarPorId(id);
 * 
 * CategoriaResponse cGR = new CategoriaResponse(); cGR.nombre =
 * categoria.getNombre(); cGR.descripcion = categoria.getDescripcion();
 * 
 * return ResponseEntity.ok(cGR); }
 * 
 * @GetMapping("/categorias") ResponseEntity<List<CategoriaResponse>>
 * listarCategorias() { List<Categoria> listaCategorias =
 * categoriaService.listarTodas(); List<CategoriaResponse>
 * listaCategoriasResponse = new ArrayList<CategoriaResponse>(); for (Categoria
 * c : listaCategorias) { CategoriaResponse cR = new CategoriaResponse();
 * cR.nombre = c.getNombre(); cR.descripcion = c.getDescripcion();
 * listaCategoriasResponse.add(cR); } return
 * ResponseEntity.ok(listaCategoriasResponse); }
 */
