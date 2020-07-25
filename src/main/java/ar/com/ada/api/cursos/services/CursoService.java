package ar.com.ada.api.cursos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.repos.CursoRepository;

@Service
public class CursoService {
    // es un atributo de la clase CursoService
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    CategoriaService categoriaService;

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
}