package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // "Apellido, Nombre"

    @Column(name = "anio_nacimiento")
    private Integer anioNacimiento;

    @Column(name = "anio_muerte")
    private Integer anioMuerte;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioMuerte) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioMuerte = anioMuerte;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }
    public Integer getAnioMuerte() { return anioMuerte; }
    public void setAnioMuerte(Integer anioMuerte) { this.anioMuerte = anioMuerte; }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + ''' +
                ", anioNacimiento=" + anioNacimiento +
                ", anioMuerte=" + anioMuerte +
                '}';
    }
}
