package com.example.parcial2.ui.paciente;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.adapters.PacientesAdapter;
import com.example.parcial2.data.local.AppDatabase;
import com.example.parcial2.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ListaPacientesActivity extends AppCompatActivity {

    private RecyclerView rvPacientes;
    private PacientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        // Inicializar RecyclerView
        rvPacientes = findViewById(R.id.rvPacientes);
        rvPacientes.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar Adapter con lista vacía
        adapter = new PacientesAdapter(new ArrayList<>());
        rvPacientes.setAdapter(adapter);

        // Observar cambios en la base de datos
        AppDatabase.getInstance(this).pacienteDao().allPacientes().observe(this, new Observer<List<Paciente>>() {
            @Override
            public void onChanged(List<Paciente> pacientes) {
                adapter.setPacientes(pacientes); // actualiza la lista en el adapter
            }
        });
    }
}
