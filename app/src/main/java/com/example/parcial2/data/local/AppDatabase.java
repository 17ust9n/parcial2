package com.example.parcial2.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.parcial2.model.Medicamento;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.model.Medico;

@Database(entities = {Medicamento.class, Paciente.class, Medico.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAOs
    public abstract MedicamentoDao medicamentoDao();
    public abstract MedicoDao medicoDao();
    public abstract PacienteDao pacienteDao();

    // Migraciones
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Ejemplo: agregamos columna 'email' a Medico en la versión 2
            database.execSQL("ALTER TABLE Medico ADD COLUMN email TEXT");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Ejemplo: agregamos columna 'vencimiento' a Medicamento en la versión 3
            database.execSQL("ALTER TABLE Medicamento ADD COLUMN vencimiento TEXT");

            // Si agregaste otras columnas a Paciente o Medico, agregá ALTER TABLE aquí
            // database.execSQL("ALTER TABLE Paciente ADD COLUMN edad INTEGER");
        }
    };

    // Singleton
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "clinica_db")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }
}
