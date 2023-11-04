package com.app.biblioteca.servicio;

import com.app.biblioteca.entidades.Imagen;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRep;
    @Transactional
    public Imagen guardarImagen(MultipartFile archivo) throws MisExcepciones {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setDataImage(archivo.getBytes());
                return imagenRep.save(imagen);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizarImagen(String id,MultipartFile archivo)throws MisExcepciones{
       validardDatos(id);
        Imagen imagen = new Imagen();
        Optional<Imagen> respuesta = imagenRep.findById(id);
        if (respuesta.isPresent()){
            imagen = respuesta.get();
            try {
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setDataImage(archivo.getBytes());
                return imagenRep.save(imagen);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    public void validardDatos(String id) throws MisExcepciones{
        if (id.isEmpty() || id==null){
            throw new MisExcepciones("El id no puede ser nulo");
        }
    }
}
