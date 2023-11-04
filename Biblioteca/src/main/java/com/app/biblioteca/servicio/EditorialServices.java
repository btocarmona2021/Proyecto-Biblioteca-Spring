package com.app.biblioteca.servicio;

import com.app.biblioteca.entidades.Editorial;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServices {

    //Persistimos la entidad
    @Autowired
    //Creamos una variable de instancia privada
    private EditorialRepositorio editorialRepositorio;

    //----------------------------------//
    @Transactional // Produce un cambio permanente en la base de datos
    public void crearEditorial(String nombre) throws MisExcepciones {

        validarDatos(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        //guardamos el objeto en la base de datos(persistir)
        this.editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditorial() {
        List<Editorial> listarEditoriales = new ArrayList<>();
        listarEditoriales = editorialRepositorio.findAll();
        return listarEditoriales;
    }

    
    public void modificarEditorial(String id, String nombre)throws MisExcepciones{
        validarDatos(nombre);
        
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(id);
        if (respuestaEditorial.isPresent()) {
            Editorial editorial = respuestaEditorial.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    private void validarDatos(String nombre) throws MisExcepciones {

        if (nombre.isEmpty() || nombre == null) {
            throw new MisExcepciones("El nombre de la editorial no puede estar vacio o nulo");
        }
    }
     public Editorial obtenerEditorial(String id){
        Editorial editorial = editorialRepositorio.getOne(id);
        return editorial;
    }
   
}

