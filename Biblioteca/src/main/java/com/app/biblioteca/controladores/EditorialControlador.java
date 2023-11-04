package com.app.biblioteca.controladores;
import com.app.biblioteca.entidades.Editorial;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.servicio.EditorialServices;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServices editorialServicio;
    
    @GetMapping("/registrar")
    public String editorial(){
        
        return "editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registraEditorial(@RequestParam String editorial,ModelMap model){
        
        try {
            editorialServicio.crearEditorial(editorial);
            
            model.put("exito", "Genial, La editorial se a guardado en nuestra base de datos");
        } catch (MisExcepciones ex) {
           model.put("error", ex.getMessage());
        return "editorial_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap model) {
        List<Editorial> lista= editorialServicio.listarEditorial();  
        model.addAttribute("listaEditorial", lista);
        
        return "lista_editorial.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarEditorial(@PathVariable String id,ModelMap model){
        Editorial editorial = editorialServicio.obtenerEditorial(id);
        model.put("editorial", editorial);
        return "editorial_modificar.html";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap model){
        try {
            editorialServicio.modificarEditorial(id, nombre);
            return "redirect:../lista";
        } catch (MisExcepciones ex) {
            model.put("error", ex.getMessage());
            return "editorial_modificar.html";
            
        }
        
    }
 
    
}
