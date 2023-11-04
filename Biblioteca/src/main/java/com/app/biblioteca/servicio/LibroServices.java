package com.app.biblioteca.servicio;

import com.app.biblioteca.entidades.Autor;
import com.app.biblioteca.entidades.Editorial;
import com.app.biblioteca.entidades.Libro;
import com.app.biblioteca.excepciones.MisExcepciones;
import com.app.biblioteca.repositorios.AutorRepositorio;
import com.app.biblioteca.repositorios.EditorialRepositorio;
import com.app.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServices {

    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional // Produce un cambio permanente en la base de datos
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MisExcepciones {

        validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();

        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> listaLibros = new ArrayList<>();
        listaLibros = libroRepositorio.findAll();
        return listaLibros;
    }

    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MisExcepciones{
        System.out.println("vamos a validar");
        validarDatos(isbn, titulo, ejemplares, idAutor, idEditorial);
        System.out.println("ya se valido");
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
       
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            
            
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            System.out.println(isbn + "/ "+ titulo +"/"+ ejemplares+"/"+ autor.getId() +"/"+ editorial.getId());
           
            libroRepositorio.save(libro);
            
        }
    }

    private void validarDatos(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MisExcepciones {
        if (isbn == null) {
            throw new MisExcepciones("El valor del Isbn no puede estar nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MisExcepciones("El título no puede estar nulo o vacio");
        }

        if (ejemplares == null) {
            throw new MisExcepciones("El valor de ejemplares no puede ser nulo");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MisExcepciones("El id del auto no puede estar vacio o nulo");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MisExcepciones("El id de la editorial no puede ser vacio o nulo");
        }
    }
    
    public Libro obtenerLibro(Long isbn){
        Libro libro = libroRepositorio.getOne(isbn);
        return libro;
    }
   
}
