package com.app.biblioteca.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    private String mime;
    private String nombre;

    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] dataImage;

    public Imagen() {
    }
}
