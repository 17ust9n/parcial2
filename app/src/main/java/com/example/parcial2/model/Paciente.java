package com.example.parcial2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Paciente {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private int edad;
    private String email;
    private String diagnostico;

    // Constructor
    public Paciente(String nombre, int edad, String email, String diagnostico) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.diagnostico = diagnostico;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
}
