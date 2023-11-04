package com.app.biblioteca.servicio;

import com.app.biblioteca.entidades.Autor;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServices {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional // Produce un cambio permanente en la base de datos
    public void crearAutor(String nombre) throws MisExcepciones {
        //validamos los datos
        validarDatos(nombre);

        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        System.out.println(autor.getId());
        this.autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        List<Autor> listarAutores = new ArrayList<>();
        listarAutores = autorRepositorio.findAll();
        return listarAutores;
    }

    public void modificarAutor(String id,String nombre) throws MisExcepciones {
        //validamos los datos
        validarDatos(nombre);

        Optional<Autor> respuestaAutor = autorRepositorio.findById(id);
        if (respuestaAutor.isPresent()) {
            Autor autor = respuestaAutor.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }

    private void validarDatos(String nombre) throws MisExcepciones {
        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("el nombre del autor no puede estar vacio o nulo");
        }
    }
    
    public Autor obtenerAutor(String id){
        Autor autor = autorRepositorio.getOne(id);
    return autor;
    }
    
}
