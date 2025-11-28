package com.example.parcial2.ui.medico;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.adapters.MedicosAdapter;
import com.example.parcial2.data.local.AppDatabase;

import java.util.ArrayList;

public class ListaMedicosActivity extends AppCompatActivity {

    private RecyclerView rvMedicos;
    private Button btnVolver;
    private MedicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Usamos el layout con descripción, botón y RecyclerView
        setContentView(R.layout.activity_medicos);

        // Vincular vistas
        rvMedicos = findViewById(R.id.rvMedicos);
        btnVolver = findViewById(R.id.btnVolverMedicos);

        // Configurar RecyclerView
        rvMedicos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicosAdapter(new ArrayList<>());
        rvMedicos.setAdapter(adapter);

        // Observar cambios en la base de datos
        AppDatabase.getInstance(this)
                .medicoDao()
                .allMedicos()
                .observe(this, adapter::setMedicos);

        // Botón de volver a MainActivity
        btnVolver.setOnClickListener(v -> finish());
    }
}
