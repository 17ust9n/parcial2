package com.example.parcial2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "turnos")
public class Turno {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombreMedico;
    private String nombrePaciente;
    private String nombreMedicamento;
    private String fecha;       // por ejemplo: "2025-12-24"
    private String hora;        // por ejemplo: "15:30"
    private String consultorio; // por ejemplo: "Consultorio 1"

    // Constructor que Room usará
    public Turno() { }

    // Constructor de conveniencia para tu lógica
    @Ignore
    public Turno(String nombreMedico, String nombrePaciente, String nombreMedicamento,
                 String fecha, String hora, String consultorio) {
        this.nombreMedico = nombreMedico;
        this.nombrePaciente = nombrePaciente;
        this.nombreMedicamento = nombreMedicamento;
        this.fecha = fecha;
        this.hora = hora;
        this.consultorio = consultorio;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }
}
