package com.demo.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max=100)
    private String nombre;

    @Size(max=250)
    private String descripcion;

    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}

    public void setNombre() {this.nombre=nombre;}
    public void setDescripcion() {this.descripcion=descripcion;}
}
