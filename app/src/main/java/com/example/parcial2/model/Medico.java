package com.example.parcial2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medico {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String email;
    private String especialidad;
    private String telefono;

    public Medico(String nombre, String email, String especialidad, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
