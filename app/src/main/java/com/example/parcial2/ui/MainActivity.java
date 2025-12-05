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
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnMedicos, btnPacientes, btnAcerca, btnLogout, btnMedicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajuste para barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Vincular botones
        btnMedicos = findViewById(R.id.btnMedicos);
        btnPacientes = findViewById(R.id.btnPacientes);
        btnMedicamentos = findViewById(R.id.btnMedicamentos);
        btnAcerca = findViewById(R.id.btnAcerca);
        btnLogout = findViewById(R.id.btnLogout);

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
}
