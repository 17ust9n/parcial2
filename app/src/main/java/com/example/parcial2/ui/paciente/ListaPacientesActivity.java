package com.example.parcial2.ui.paciente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ListaPacientesActivity extends AppCompatActivity {

    private TextView tvNombre, tvEdad, tvEmail, tvDiagnostico;
    private ImageButton btnAnterior, btnSiguiente;
    private Button btnAgregar, btnModificar, btnEliminar, btnVolver;
    private ClinicaRepository repo;

    private List<Paciente> pacientes = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas
        tvNombre = findViewById(R.id.tvNombrePaciente);
        tvEdad = findViewById(R.id.tvEdadPaciente);
        tvEmail = findViewById(R.id.tvEmailPaciente);
        tvDiagnostico = findViewById(R.id.tvDiagnosticoPaciente);

        btnAnterior = findViewById(R.id.btnAnteriorPaciente);
        btnSiguiente = findViewById(R.id.btnSiguientePaciente);

        btnAgregar = findViewById(R.id.btnAgregarPaciente);
        btnModificar = findViewById(R.id.btnModificarPaciente);
        btnEliminar = findViewById(R.id.btnEliminarPaciente);
        btnVolver = findViewById(R.id.btnVolverPacientes);

        // Observamos los pacientes
        repo.obtenerPacientes().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                agregarPacientesPrueba();
            } else {
                pacientes = lista;
                mostrarPaciente(index);
            }
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> {
            if (index > 0) index--;
            mostrarPaciente(index);
        });

        btnSiguiente.setOnClickListener(v -> {
            if (index < pacientes.size() - 1) index++;
            mostrarPaciente(index);
        });

        btnVolver.setOnClickListener(v -> finish());

        btnAgregar.setOnClickListener(v -> {
            // lógica para agregar paciente
        });

        btnModificar.setOnClickListener(v -> {
            // lógica para modificar paciente
        });

        btnEliminar.setOnClickListener(v -> {
            // lógica para eliminar paciente
        });
    }

    private void mostrarPaciente(int i) {
        if (pacientes.isEmpty()) return;

        Paciente p = pacientes.get(i);
        tvNombre.setText(p.getNombre());
        tvEdad.setText("Edad: " + p.getEdad());
        tvEmail.setText("Email: " + p.getEmail());
        tvDiagnostico.setText("Diagnóstico: " + p.getDiagnostico());
    }

    private void agregarPacientesPrueba() {
        List<Paciente> prueba = new ArrayList<>();
        prueba.add(new Paciente("Juan Pérez", 30, "juan@correo.com", "Gripe"));
        prueba.add(new Paciente("Ana Gómez", 25, "ana@correo.com", "Resfriado"));
        prueba.add(new Paciente("Luis Martínez", 40, "luis@correo.com", "Dolor de cabeza"));

        for (Paciente p : prueba) {
            repo.insertarPaciente(p);
        }
    }
}
