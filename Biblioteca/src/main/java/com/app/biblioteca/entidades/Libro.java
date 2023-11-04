package com.app.biblioteca.entidades;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Getter
@Setter
@ToString
public class Libro {
    
    @Id
    private Long Isbn;
    private String titulo;
    private Integer ejemplares;
    @Temporal(TemporalType.DATE)  
    private Date alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editorial editorial;
    public Libro() {
    }

}
