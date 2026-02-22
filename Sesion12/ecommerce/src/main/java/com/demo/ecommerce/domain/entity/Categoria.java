package com.demo.ecommerce.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    @Column(length = 100)
    private String descripcion;

    //getter /setter    Lombok (@getter @setter)
    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}

    public void setId() {this.id=id;}
    public void setNombre(String nombre) {this.nombre= nombre;}
    public void setDescripcion(String descripcion) {this.descripcion= descripcion;}

}
