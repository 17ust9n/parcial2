package com.example.parcial2.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.parcial2.model.Paciente;

@Dao
public interface PacienteDao {

    // Insertar un paciente
    @Insert
    long insertar(Paciente paciente);

    // Actualizar un paciente
    @Update
    void actualizar(Paciente paciente);

    // Eliminar un paciente
    @Delete
    void eliminar(Paciente paciente);

    // Obtener todos los pacientes como LiveData
    @Query("SELECT * FROM Paciente ORDER BY nombre ASC")
    LiveData<java.util.List<Paciente>> allPacientes();

    // Obtener paciente por ID
    @Query("SELECT * FROM Paciente WHERE id = :id LIMIT 1")
    Paciente obtenerPorId(int id);

    // Nota: método de filtro por médico eliminado porque no tenemos medicoId
}
