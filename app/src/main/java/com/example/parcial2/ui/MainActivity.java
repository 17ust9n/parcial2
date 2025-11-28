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
import com.google.firebase.auth.FirebaseAuth;

import com.example.parcial2.ui.paciente.ListaPacientesActivity;
import com.example.parcial2.ui.medico.ListaMedicosActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMedicos, btnPacientes, btnAcerca, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajuste para barras de sistema (layout debe tener id="main")
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

        // ======= ACCIONES DE LOS BOTONES =======

        // Médicos → MedicosActivity
        btnMedicos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaMedicosActivity.class);
            startActivity(intent);
        });

        // Pacientes → PacientesActivity
        btnPacientes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaPacientesActivity.class);
            startActivity(intent);
        });

        // Acerca
        btnAcerca.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AcercaActivity.class);
            startActivity(intent);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
