package com.app.biblioteca.controladores;

import com.app.biblioteca.entidades.Autor;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.servicio.AutorServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServices autorServicio;

    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam() String nombre, ModelMap model) {
        
        try {
            autorServicio.crearAutor(nombre);
            model.put("exito", "El autor se guardó exitosamente");
            return "index.html";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "autor_form.html";
        }
        
    }

    @GetMapping("/lista")
    public String listarAutores(ModelMap modelo) {
        List<Autor> listaAutores = autorServicio.listarAutores();
        modelo.addAttribute("autores", listaAutores);

        return "lista_autores.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap model) {
        Autor autor = autorServicio.obtenerAutor(id);
        model.put("autor", autor);
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model) {
        try {
            autorServicio.modificarAutor(id, nombre);
            return "redirect:../lista";
        } catch (MisExcepciones ex) {
            model.put("errror", ex.getMessage());
            return "autor_modificar.html";
        }
    }

}
