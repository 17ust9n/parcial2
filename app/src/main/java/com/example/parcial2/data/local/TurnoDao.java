package com.example.parcial2.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.parcial2.model.Turno;

import java.util.List;

@Dao
public interface TurnoDao {

    @Query("SELECT * FROM turnos ORDER BY id DESC")
    LiveData<List<Turno>> obtenerTurnos();

    @Insert
    long insertarTurno(Turno turno);   // 👈 CAMBIO AQUÍ

    @Update
    void actualizarTurno(Turno turno);

    @Delete
    void eliminarTurno(Turno turno);
}
