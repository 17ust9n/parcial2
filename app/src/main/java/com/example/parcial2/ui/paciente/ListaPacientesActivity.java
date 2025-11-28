package com.example.parcial2.ui.paciente;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.adapters.PacientesAdapter;
import com.example.parcial2.data.local.AppDatabase;

import java.util.ArrayList;

public class ListaPacientesActivity extends AppCompatActivity {

    private RecyclerView rvPacientes;
    private Button btnVolver;
    private PacientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        rvPacientes = findViewById(R.id.rvPacientes);
        btnVolver = findViewById(R.id.btnVolverPacientes);

        rvPacientes.setLayoutManager(new LinearLayoutManager(this));
        rvPacientes.setAdapter(new PacientesAdapter(new ArrayList<>()));

        AppDatabase.getInstance(this)
                .pacienteDao()
                .allPacientes()
                .observe(this, pacientes -> {
                    ((PacientesAdapter) rvPacientes.getAdapter()).setPacientes(pacientes);
                });

        btnVolver.setOnClickListener(v -> finish());
    }
}
