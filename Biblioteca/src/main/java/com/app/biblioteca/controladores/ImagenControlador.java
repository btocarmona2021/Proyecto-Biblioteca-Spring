package com.app.biblioteca.controladores;

import com.app.biblioteca.entidades.Usuario;
import com.app.biblioteca.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private UsuarioServicio usuarioServ;

    @GetMapping("/perfil/{id}")
    public ResponseEntity <byte[]> imagenUsuario(@PathVariable String id){
        Usuario usuario = usuarioServ.obtenerUno(id);
        byte[] imagen= usuario.getImagen().getDataImage();
        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imagen,header, HttpStatus.OK);
    }
    @GetMapping("/modificar/{id}")
    public void modificarImagen(@PathVariable String id, MultipartFile archivo){
        Usuario usuario = usuarioServ.obtenerUno(id);

    }
}
