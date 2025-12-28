package com.example.parcial2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Medico;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.model.Medicamento;
import com.example.parcial2.ui.login.LoginActivity;
import com.example.parcial2.ui.medico.ListaMedicosActivity;
import com.example.parcial2.ui.paciente.ListaPacientesActivity;
import com.example.parcial2.ui.medicamento.ListaMedicamentosActivity;
import com.example.parcial2.ui.turno.ListaTurnosActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnMedicos, btnPacientes, btnMedicamentos,
            btnTurnos, btnAcerca, btnLogout;

    private ClinicaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajuste para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Inicializar repository
        repo = new ClinicaRepository(getApplication());

        // Cargar datos iniciales SOLO si la base está vacía
        agregarMedicosPrueba();
        agregarPacientesPrueba();
        agregarMedicamentosPrueba();

        // Vincular botones
        btnMedicos = findViewById(R.id.btnMedicos);
        btnPacientes = findViewById(R.id.btnPacientes);
        btnMedicamentos = findViewById(R.id.btnMedicamentos);
        btnTurnos = findViewById(R.id.btnTurnos);
        btnAcerca = findViewById(R.id.btnAcerca);
        btnLogout = findViewById(R.id.btnLogout);

        // Navegación
        btnMedicos.setOnClickListener(v ->
                startActivity(new Intent(this, ListaMedicosActivity.class)));

        btnPacientes.setOnClickListener(v ->
                startActivity(new Intent(this, ListaPacientesActivity.class)));

        btnMedicamentos.setOnClickListener(v ->
                startActivity(new Intent(this, ListaMedicamentosActivity.class)));

        btnTurnos.setOnClickListener(v ->
                startActivity(new Intent(this, ListaTurnosActivity.class)));

        btnAcerca.setOnClickListener(v ->
                startActivity(new Intent(this, AcercaActivity.class)));

        // Logout
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleClient =
                    GoogleSignIn.getClient(this, gso);

            googleClient.signOut().addOnCompleteListener(task -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        });
    }

    /* ===================== DATOS DE PRUEBA ===================== */

    private void agregarMedicosPrueba() {
        repo.obtenerMedicos().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                repo.insertarMedico(new Medico(
                        "Dra. Ana Gómez", "MAT456",
                        "Pediatría", "ana@correo.com"
                ));
                repo.insertarMedico(new Medico(
                        "Dr. Juan Pérez", "MAT123",
                        "Cardiología", "juan@correo.com"
                ));
            }
        });
    }

    private void agregarPacientesPrueba() {
        repo.obtenerPacientes().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                repo.insertarPaciente(new Paciente(
                        "Luis Martínez", 40,
                        "luis@correo.com", "Dolor de cabeza"
                ));
                repo.insertarPaciente(new Paciente(
                        "María López", 32,
                        "maria@correo.com", "Alergia"
                ));
            }
        });
    }

    private void agregarMedicamentosPrueba() {
        repo.obtenerMedicamentos().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                repo.insertarMedicamento(new Medicamento(
                        "Paracetamol", "Alivia el dolor y baja la fiebre",
                        "Bayer", "Tabletas de 500mg",
                        "Náuseas, mareos", "$50",
                        "2025-12-31"
                ));
                repo.insertarMedicamento(new Medicamento(
                        "Ibuprofeno", "Analgésico y antiinflamatorio",
                        "Pfizer", "400mg en cápsula",
                        "Dolor de estómago", "$70",
                        "2025-11-30"
                ));
            }
        });
    }
}
