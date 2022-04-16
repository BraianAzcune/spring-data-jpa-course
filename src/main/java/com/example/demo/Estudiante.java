package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Table(name = "estudiante", uniqueConstraints = {
        @UniqueConstraint(name = "estudiante_correo_unique", columnNames = "correo")
})
@Entity(name = "estudiante")
@Getter
@Setter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudiante_seq")
    @SequenceGenerator(name = "estudiante_seq", sequenceName = "estudiante_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "correo", nullable = false, length = 320)
    private String correo;
    @Column(name = "edad", nullable = false)
    private Integer edad;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, String correo, Integer edad) {
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
