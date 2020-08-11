package ar.com.ada.api.cursos.models.response;

import java.util.*;

import ar.com.ada.api.cursos.entities.Categoria;

public class CursoEstudianteResponse {
    public Integer cursoId;
    public String nombre;
    public String descripcion;
    public List<Categoria> categorias = new ArrayList<>();
    public List<DocenteSimplificadoResponse> docentes = new ArrayList<>();
    public Integer duracionHoras;
} 