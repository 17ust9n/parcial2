package com.example.parcial2.ui.medico;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Medico;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicosActivity extends AppCompatActivity {

    private TextView tvNombre, tvMatricula, tvEspecialidad, tvEmail;
    private ImageButton btnAnterior, btnSiguiente;
    private Button btnAgregar, btnModificar, btnEliminar, btnVolver;

    private ClinicaRepository repo;
    private List<Medico> medicos = new ArrayList<>();
    private int indiceActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        // Vistas
        tvNombre = findViewById(R.id.tvNombreMedico);
        tvMatricula = findViewById(R.id.tvMatriculaMedico);
        tvEspecialidad = findViewById(R.id.tvEspecialidadMedico);
        tvEmail = findViewById(R.id.tvEmailMedico);

        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        btnAgregar = findViewById(R.id.btnAgregarMedico);
        btnModificar = findViewById(R.id.btnModificarMedico);
        btnEliminar = findViewById(R.id.btnEliminarMedico);
        btnVolver = findViewById(R.id.btnVolverMedicos);

        // Repositorio
        repo = new ClinicaRepository(getApplication());

        // Cargar médicos de prueba
        cargarMedicosDePrueba();

        // Mostrar primer médico
        mostrarMedicoActual();

        // Flechas
        btnAnterior.setOnClickListener(v -> {
            if (indiceActual > 0) {
                indiceActual--;
                mostrarMedicoActual();
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (indiceActual < medicos.size() - 1) {
                indiceActual++;
                mostrarMedicoActual();
            }
        });

        // Botones de acción
        btnAgregar.setOnClickListener(v -> {
            // Lógica para agregar médico
        });

        btnModificar.setOnClickListener(v -> {
            // Lógica para modificar médico
        });

        btnEliminar.setOnClickListener(v -> {
            // Lógica para eliminar médico
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void mostrarMedicoActual() {
        if (medicos.isEmpty()) return;
        Medico medico = medicos.get(indiceActual);
        tvNombre.setText(medico.getNombre());
        tvMatricula.setText("Matrícula: " + medico.getTelefono()); // o id
        tvEspecialidad.setText("Especialidad: " + medico.getEspecialidad());
        tvEmail.setText("Email: " + medico.getEmail());
    }

    private void cargarMedicosDePrueba() {
        medicos.add(new Medico("Dr. Juan Pérez", "juan@clinica.com", "Cardiología", "12345678"));
        medicos.add(new Medico("Dra. Ana Gómez", "ana@clinica.com", "Pediatría", "87654321"));
        medicos.add(new Medico("Dr. Luis Martínez", "luis@clinica.com", "Neurología", "11223344"));
    }
}

