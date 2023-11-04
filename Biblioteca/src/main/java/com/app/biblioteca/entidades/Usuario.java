package com.app.biblioteca.entidades;
import com.app.biblioteca.enumeraciones.Rol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid" , strategy = "uuid2")
    private  String id;
    private String nombre;
    private String correo;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Imagen imagen;
    private Boolean estado;

    public Usuario() {
    }
}
