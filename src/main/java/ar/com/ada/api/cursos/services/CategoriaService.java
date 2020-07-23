package ar.com.ada.api.cursos.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import ar.com.ada.api.cursos.entities.*;
import ar.com.ada.api.cursos.repos.CategoriaRepository;
import java.util.*;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repoCategoria;

    public void crearCategoria(Categoria categoria) {
        repoCategoria.save(categoria);
    }

    public Categoria crearCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        repoCategoria.save(categoria);
        return categoria;
    }

    public Categoria buscarPorId(Integer id) {
        Optional<Categoria> opCategoria = repoCategoria.findById(id);

        // Si tiene un valor de categoria en el elemento que trajo.
        // Camion con heladera dentro. hasta que no abrimos la puerta no sabemos si la
        // trajo.
        if (opCategoria.isPresent())
            return opCategoria.get();
        else
            return null;

    }
} 