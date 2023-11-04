package com.app.biblioteca.controladores;

import com.app.biblioteca.entidades.Autor;
import com.app.biblioteca.entidades.Editorial;
import com.app.biblioteca.entidades.Libro;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.servicio.AutorServices;
import com.app.biblioteca.servicio.EditorialServices;
import com.app.biblioteca.servicio.LibroServices;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libros")
public class LibroControlador {

    @Autowired
    private LibroServices libroServicio;
    @Autowired
    private AutorServices autorServicio;
    @Autowired
    private EditorialServices editorialServicio;

    @GetMapping("/registrar")
    public String registrarLibro(ModelMap modelo) {
        opciones(modelo);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registrado(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares, @RequestParam String idautor, @RequestParam String ideditorial, ModelMap modelo) {

        try {

            libroServicio.crearLibro(isbn, titulo, ejemplares, idautor, ideditorial);
            modelo.put("exito", "El libro fue cargado correctamente");

        } catch (MisExcepciones ex) {

            modelo.put("error", ex.getMessage());

            opciones(modelo);

            return "libro_form.html";
        }
        return "index.html";
    }

    public void opciones(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditorial();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
    }

    @GetMapping("/lista")
    public String listarLibros(ModelMap model) {
        List<Libro> listalibros = libroServicio.listarLibros();
        model.addAttribute("listadolibros", listalibros);
        return "lista_libros.html";
    }
    
    @GetMapping("/modificar/{isbn}")
    public String modificarLibro(@PathVariable Long isbn,ModelMap model){
       Libro libro =libroServicio.obtenerLibro(isbn);
        opciones(model);
        model.put("libro", libro);
        
        return "libro_modificar.html";
    }   
    
    @PostMapping("/modificar/{isbn}")
    public String modificarLibro(@PathVariable Long isbn,String titulo,Integer ejemplares,String idautor,String ideditorial,ModelMap model){
        System.out.println(isbn +"//"+ titulo +"//"+ ejemplares +"//"+ idautor + "///" + ideditorial);
        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idautor, ideditorial);
          
            return "redirect:../lista";
        } catch (MisExcepciones ex) {
           model.put("error", ex.getMessage());
           return "libro_modificar.html";
        } catch(Exception ex){
            System.out.println(ex.getMessage());
              return "libro_modificar.html";
        }
        
    }
}
