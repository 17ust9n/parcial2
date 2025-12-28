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
import com.example.parcial2.model.Turno;

@Database(entities = {Medicamento.class, Paciente.class, Medico.class,
        Turno.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    // DAOs
    public abstract MedicamentoDao medicamentoDao();
    public abstract MedicoDao medicoDao();
    public abstract PacienteDao pacienteDao();
    public abstract TurnoDao turnoDao();

    // Migraciones existentes
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Medico ADD COLUMN email TEXT");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Medicamento ADD COLUMN vencimiento TEXT");
        }
    };

    // Migración 3 a 4 (la incorrecta original - la dejamos para no romper)
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `turnos` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nombreMedico` TEXT, " +
                    "`matriculaMedico` TEXT, " +
                    "`especialidadMedico` TEXT, " +
                    "`nombrePaciente` TEXT, " +
                    "`edadPaciente` INTEGER NOT NULL, " +
                    "`diagnosticoPaciente` TEXT, " +
                    "`nombreMedicamento` TEXT, " +
                    "`dosisMedicamento` TEXT, " +
                    "`fecha` TEXT, " +
                    "`hora` TEXT)");
        }
    };

    // NUEVA Migración 4 a 5 (corrige la tabla turnos)
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Eliminar la tabla incorrecta
            database.execSQL("DROP TABLE IF EXISTS `turnos`");

            // Crear la tabla correcta según el modelo Turno
            database.execSQL("CREATE TABLE IF NOT EXISTS `turnos` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`nombreMedico` TEXT, " +
                    "`nombrePaciente` TEXT, " +
                    "`nombreMedicamento` TEXT, " +
                    "`fecha` TEXT, " +
                    "`hora` TEXT, " +
                    "`consultorio` TEXT)");
        }
    };

    // Singleton
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "clinica_db")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build();
        }
        return INSTANCE;
    }
}