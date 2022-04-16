package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class Estudiante {

    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private Integer edad;

    public Estudiante() {
    }

    public Estudiante(Long id, String nombre, String apellido, String correo, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Estudiante [apellido=" + apellido + ", correo=" + correo + ", edad=" + edad + ", id=" + id + ", nombre="
                + nombre + "]";
    }

}
