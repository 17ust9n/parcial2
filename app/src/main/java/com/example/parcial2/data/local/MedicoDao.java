package com.example.parcial2.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.parcial2.model.Medico;

import java.util.List;

@Dao
public interface MedicoDao {

    @Insert
    long insertar(Medico medico);

    @Update
    void actualizar(Medico medico);

    @Delete
    void eliminar(Medico medico);

    @Query("SELECT * FROM Medico ORDER BY nombre ASC")
    LiveData<List<Medico>> allMedicos();

    @Query("SELECT * FROM Medico WHERE id = :id LIMIT 1")
    Medico obtenerPorId(int id);

    // NUEVO: eliminar todos los médicos
    @Query("DELETE FROM Medico")
    void eliminarTodos();
}
