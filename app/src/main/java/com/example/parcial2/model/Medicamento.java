package com.example.parcial2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medicamento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String uso;
    private String laboratorio;
    private String dosis;
    private String efectos;          // efectos secundarios
    private String precio;
    private String vencimiento;

    public Medicamento() {}

    public Medicamento(String nombre, String uso, String laboratorio,
                       String dosis, String efectos, String precio,
                       String vencimiento) {
        this.nombre = nombre;
        this.uso = uso;
        this.laboratorio = laboratorio;
        this.dosis = dosis;
        this.efectos = efectos;
        this.precio = precio;
        this.vencimiento = vencimiento;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUso() { return uso; }
    public void setUso(String uso) { this.uso = uso; }

    public String getLaboratorio() { return laboratorio; }
    public void setLaboratorio(String laboratorio) { this.laboratorio = laboratorio; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getEfectos() { return efectos; }
    public void setEfectos(String efectos) { this.efectos = efectos; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public String getVencimiento() { return vencimiento; }
    public void setVencimiento(String vencimiento) { this.vencimiento = vencimiento; }
}
