package com.app.biblioteca.repositorios;

import com.app.biblioteca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
            public Usuario buscarxCorreo(@Param("correo") String correo);

}
