package com.example.parcial2.ui.medico;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.adapters.MedicosAdapter;
import com.example.parcial2.data.local.AppDatabase;
import com.example.parcial2.model.Medico;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicosActivity extends AppCompatActivity {

    private RecyclerView rvMedicos;
    private MedicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicos);

        // Inicializar RecyclerView
        rvMedicos = findViewById(R.id.rvMedicos);
        rvMedicos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar Adapter con lista vacía
        adapter = new MedicosAdapter(new ArrayList<>());
        rvMedicos.setAdapter(adapter);

        // Observar cambios en la base de datos
        AppDatabase.getInstance(this).medicoDao().allMedicos().observe(this, new Observer<List<Medico>>() {
            @Override
            public void onChanged(List<Medico> medicos) {
                adapter.setMedicos(medicos); // actualiza la lista en el adapter
            }
        });
    }
}
