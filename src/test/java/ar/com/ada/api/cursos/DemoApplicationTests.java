package ar.com.ada.api.cursos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.cursos.entities.Categoria;
import ar.com.ada.api.cursos.repos.CategoriaRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import ar.com.ada.api.cursos.services.*;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	CategoriaRepository repoCategoria;
	@Autowired
	CategoriaService categoriaService;

	@Test
	void crearCategoriaSinCursoTest() {
		

		Categoria categoria = new Categoria();

		categoria.setNombre("Matematicas");
		categoria.setDescripcion("vemos algebra");
		/*
		 * Esto de abajo ya no nos interesa porque evoluciono a un servicio
		 * CategoriaService.crearCategoria Categoria categoria = new Categoria();
		 * 
		 * categoria.setNombre("Matematicas");
		 * categoria.setDescripcion("vemos algebra");
		 * 
		 * repoCategoria.save(categoria);
		 */
		Categoria categoria2 = categoriaService.crearCategoria("Matematicas", "vemos algebra");

		repoCategoria.save(categoria2);

	}

		// si id era int hubiera sido assertTrue(categoria.getCategoriaId() > 0)
		// como el id es Integer se utiliza el compareTo que devuelve:
		// -1 si a < parametro
        void crearCategoriaSinCursoTest(Categoria categoria) {

			
			Integer nuevaCategoriaId = categoria.getCategoriaId();

		// Busca la categoria que tiene el ID en la base de datos
		// particularmente el findById del repo: devuelve un Optional
		// Optional va a haber que checkear si el valor existe o no.
		Optional<Categoria> catDesdeDBResultado = repoCategoria.findById(nuevaCategoriaId);
		/*
		 * Esta es la forma sin SERVICE // Busca la categoria que tiene el ID en la base
		 * de datos // particularmente el findById del repo: devuelve un Optional //
		 * Optional va a haber que checkear si el valor existe o no. Optional<Categoria>
		 * catDesdeDBResultado = categoriaService.findById(nuevaCategoriaId);
		 * 
		 * assertTrue(catDesdeDBResultado.isPresent());
		 * 
		 * Categoria categoriaDesdeDB = catDesdeDBResultado.get();
		 */

		assertTrue(catDesdeDBResultado.isPresent());
		Categoria categoriaDesdeDB = categoriaService.buscarPorId(nuevaCategoriaId);

		Categoria categoriaDesdeDB2 = catDesdeDBResultado.get();
		assertTrue(categoriaDesdeDB2 != null);

		assertEquals("Matematicas", categoriaDesdeDB.getNombre());
		assertEquals("vemos algebra", categoriaDesdeDB.getDescripcion());
	}
}

/*
 * Agregamos afirmaciones adicionales "ASSERT" para ver si la categoria se creo
 * en la base de datos leyendola desde la base de datos y comparando valores
 */
/*
 * 
 * void crearCategoriaSinCursoTest() { tituloTestInicio("Categoria sin Curso");
 * Categoria categoria = new Categoria(); * 
 * categoria.setNombre("Matematicas");
 * categoria.setDescripcion("vemos algebra");
 * 
 * repoCategoria.save(categoria); // si id era int hubiera sido
 * assertTrue(categoria.getCategoriaId() > 0) // como el id es Integer se
 * utiliza el compareTo que devuelve: // -1 si a < parametro // 0 si a == 1 // 1
 * si a > parametro assertTrue(categoria.getCategoriaId().compareTo(0) == 1);
 * 
 * Integer nuevaCategoriaId = categoria.getCategoriaId();
 * 
 * // Busca la categoria que tiene el ID en la base de datos // particularmente
 * el findById del repo: devuelve un Optional // Optional va a haber que
 * checkear si el valor existe o no. Optional<Categoria> catDesdeDBResultado =
 * repoCategoria.findById(nuevaCategoriaId);
 * 
 * assertTrue(catDesdeDBResultado.isPresent());
 * 
 * Categoria categoriaDesdeDB = catDesdeDBResultado.get();
 * 
 * assertEquals("Matematicas", categoriaDesdeDB.getNombre());
 * assertEquals("vemos algebra", categoriaDesdeDB.getDescripcion());
 * 
 * tituloTestFin("Categoria sin Curso");
 * 
 * }
 * 
 * void tituloTestInicio(String titulo) {
 * System.out.println("*************************************");
 * System.out.println("*************************************");
 * System.out.println("INICIO TEST: " + titulo);
 * System.out.println("*************************************");
 * System.out.println("*************************************"); }
 * 
 * void tituloTestFin(String titulo) {
 * System.out.println("*************************************");
 * System.out.println("*************************************");
 * System.out.println("FIN TEST OK:  " + titulo);
 * System.out.println("*************************************");
 * System.out.println("*************************************"); }
 * 
 * } //TODOS LOS ASSERT SON AFIRMACIONES QUE SE VAN A EJECUTAR PARA QUE EL
 * TESTING NO FALLE
 */
