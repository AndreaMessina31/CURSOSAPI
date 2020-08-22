package ar.com.ada.api.cursos.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.entities.Usuario.TipoUsuarioEnum;
import ar.com.ada.api.cursos.models.request.CategoriaModifRequest;
import ar.com.ada.api.cursos.models.response.CategoriaResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.*;

@RestController
public class CategoriaController {

    // Declarar un service

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    UsuarioService usuarioService;

    // Post: que recibimos algo, que nos permite instanciar una Categoria y ponerle
    // datos.

     // Autorizacion Forma 1
    // Metodo de verificacion 1 del checkeo que el usuario que consulta/ejecuta sea el que
    // corresponde
    // Usamos el "Principal": que es una abstraccion que nos permite acceder al
    // usuario que esta logueado

    @PostMapping("/api/categorias")
    public ResponseEntity<GenericResponse> crearCategoria(Principal principal, @RequestBody Categoria categoria) {

        Usuario usuario = usuarioService.buscarPorUsername(principal.getName());

        if (usuario.getTipoUsuarioId() != TipoUsuarioEnum.STAFF) {
            // chau chau y le damos un 403: Forbidden
            // Este le avismos que
            // return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            // en vez de tirar un 403, tiramos un 404(Not Found) y le mentimos.
            // en este caso ni siquiera le contamos qeu hay algo ahi como para que pueda
            // seguir intentando.
            return ResponseEntity.notFound().build();
        }

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
    // Metodo Verificacion 2: haciendo lo mismo que antes, pero usando
    // Spring Expression LANGUAGE(magic)
    // Aca el principal es el User, este principal no es el mismo principal del
    // metodo anterior
    // pero apunta a uno parecido(el de arriba es el principal authentication)
    // https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html

    @PutMapping(("/api/categorias/{id}"))
    @PreAuthorize("@usuarioService.buscarPorUsername(principal.getUsername()).getTipoUsuarioId().getValue() == 3") //En este caso quiero que sea STAFF(3)
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

// Autorizacion Modo 3
    // Metodo Verificacion 3: haciendo lo mismo que antes, pero leyendo
    // desde el el authority. O sea , cuando creamos el User para el UserDetails(no
    // el usuario)
    // Le seteamos una autoridad sobre el tipo de usuario
    // Esto lo que hace es preguntar si tiene esa autoridad seteada.
    // Dentro de este, tenemos 2 formas de llenar el Authority
    // Llenandolo desde la Base de datos, o desde el JWT
    // Desde la DB nos da mas seguridad pero cada vez que se ejecute es ir a buscar
    // a la DB
    // Desde el JWT, si bien exponemos el entityId, nos permite evitarnos ir a la
    // db.
    // Este CLAIM lo podemos hacer con cualquier propiedad que querramos mandar
    // al JWT

    @GetMapping("/api/categorias/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
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
