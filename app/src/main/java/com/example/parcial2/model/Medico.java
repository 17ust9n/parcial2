package com.example.parcial2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medico {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String matricula;
    private String especialidad;
    private String email;

    public Medico(String nombre, String matricula, String especialidad, String email) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.especialidad = especialidad;
        this.email = email;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
