package com.example.parcial2.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.parcial2.model.Medicamento;

import java.util.List;

@Dao
public interface MedicamentoDao {

    @Insert
    long insertar(Medicamento medicamento);

    @Update
    void actualizar(Medicamento medicamento);

    @Delete
    void eliminar(Medicamento medicamento);

    // Método renombrado para coincidir con ClinicaRepository
    @Query("SELECT * FROM Medicamento ORDER BY nombre ASC")
    LiveData<List<Medicamento>> allMedicamentos();

    @Query("SELECT * FROM Medicamento WHERE id = :id LIMIT 1")
    LiveData<Medicamento> obtenerPorId(int id);
}
