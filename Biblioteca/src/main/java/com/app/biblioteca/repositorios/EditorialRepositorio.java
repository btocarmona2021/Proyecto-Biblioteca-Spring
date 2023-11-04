package com.app.biblioteca.repositorios;

import com.app.biblioteca.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{

    
}
