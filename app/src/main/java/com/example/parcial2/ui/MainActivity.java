package com.example.parcial2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial2.R;
import com.example.parcial2.ui.login.LoginActivity;
import com.example.parcial2.ui.paciente.ListaPacientesActivity;
import com.example.parcial2.ui.medico.ListaMedicosActivity;
import com.example.parcial2.ui.medicamento.ListaMedicamentosActivity;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.model.Medico;
import com.example.parcial2.model.Medicamento;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnMedicos, btnPacientes, btnAcerca, btnLogout, btnMedicamentos;
    private ClinicaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new ClinicaRepository(getApplication());

        // Ajuste para barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Vincular botones
        btnMedicos = findViewById(R.id.btnMedicos);
        btnPacientes = findViewById(R.id.btnPacientes);
        btnAcerca = findViewById(R.id.btnAcerca);
        btnLogout = findViewById(R.id.btnLogout);
        btnMedicamentos = findViewById(R.id.btnMedicamentos);

        // Inicializar datos de prueba de forma segura
        inicializarDatosDePrueba();

        // ======= ACCIONES DE LOS BOTONES =======
        btnMedicos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListaMedicosActivity.class)));
        btnPacientes.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListaPacientesActivity.class)));
        btnMedicamentos.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListaMedicamentosActivity.class)));
        btnAcerca.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AcercaActivity.class)));
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void inicializarDatosDePrueba() {
        // Ejecutamos en un hilo aparte para no bloquear la UI
        new Thread(() -> {
            // Insertamos médicos de prueba con email
            repo.insertarMedico(new Medico("Dr. López", "12345", "Cardiología", "lopez@clinica.com"));
            repo.insertarMedico(new Medico("Dra. Martínez", "67890", "Pediatría", "martinez@clinica.com"));

            // Insertamos pacientes de prueba
            repo.insertarPaciente(new Paciente("Juan Pérez", 30, "juan@mail.com", "Sin diagnóstico"));
            repo.insertarPaciente(new Paciente("Ana Gómez", 25, "ana@mail.com", "Sin diagnóstico"));

            // Insertamos medicamentos de prueba
            repo.insertarMedicamento(new Medicamento(
                    "Paracetamol", "Dolor y fiebre", "Bayer", "500mg", "Ninguno", "200", "12/2025"));
            repo.insertarMedicamento(new Medicamento(
                    "Ibuprofeno", "Inflamación", "Pfizer", "400mg", "Náuseas", "300", "06/2026"));
        }).start();
    }
}
