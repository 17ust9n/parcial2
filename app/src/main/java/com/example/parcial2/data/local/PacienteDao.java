package com.example.parcial2.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.parcial2.model.Paciente;

import java.util.List;

@Dao
public interface PacienteDao {

    @Insert
    long insertar(Paciente paciente);

    @Update
    void actualizar(Paciente paciente);

    @Delete
    void eliminar(Paciente paciente);

    // Obtener todos los pacientes como LiveData, ordenados por nombre
    @Query("SELECT * FROM Paciente ORDER BY nombre ASC")
    LiveData<List<Paciente>> allPacientes();

    // Obtener un paciente por ID
    @Query("SELECT * FROM Paciente WHERE id = :id LIMIT 1")
    Paciente obtenerPacientePorId(int id);

    // NUEVO: eliminar todos los pacientes
    @Query("DELETE FROM Paciente")
    void eliminarTodos();

}
