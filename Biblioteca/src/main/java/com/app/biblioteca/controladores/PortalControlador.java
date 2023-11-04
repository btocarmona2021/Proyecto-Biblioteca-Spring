package com.app.biblioteca.controladores;

import com.app.biblioteca.entidades.Usuario;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {


    @Autowired
    private UsuarioServicio usuarioServ;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("registrar")
    public String registrar() {
        return "registrar.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o contraseña erroneos");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        if (usuario.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String correo, @RequestParam String password, String verificacion, MultipartFile archivo, ModelMap model) {
        try {
            usuarioServ.crearUsuario(archivo, nombre, correo, password, verificacion);
            model.put("exito", "El usuario ha sido registrado satisfactoriamente");
            return "index.html";
        } catch (MisExcepciones e) {
            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("correo", correo);
            return "registrar.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")

    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap model) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        model.put("usuario", usuario);
        model.put("session", session);
        return "modificar_usuario.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id,String nombre, String correo,MultipartFile archivo,ModelMap model) {
        try {
            usuarioServ.modificarUsuario(id,nombre,correo,archivo);
            model.put("exito","Se han realizado las modificaciones");
            return "redirect:/inicio";
        } catch (MisExcepciones e) {
          model.put("error",e.getMessage());
            return "modificar_usuario.html";

        }
    }
}
