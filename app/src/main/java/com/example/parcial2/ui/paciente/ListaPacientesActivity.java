package com.example.parcial2.ui.paciente;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Paciente;
import com.google.android.material.snackbar.Snackbar;

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

        // Observar pacientes desde la BD
        repo.obtenerPacientes().observe(this, lista -> {
            pacientes = (lista != null) ? lista : new ArrayList<>();
            index = 0;
            mostrarPacienteIndex(index);
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> mostrarPacienteIndex(index - 1));
        btnSiguiente.setOnClickListener(v -> mostrarPacienteIndex(index + 1));

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());

        // CRUD
        btnAgregar.setOnClickListener(v -> mostrarDialogoPaciente(true));
        btnModificar.setOnClickListener(v -> {
            if (!pacientes.isEmpty()) mostrarDialogoPaciente(false);
        });
        btnEliminar.setOnClickListener(v -> {
            if (pacientes.isEmpty()) return;

            repo.eliminarPaciente(pacientes.get(index));
            Snackbar.make(findViewById(android.R.id.content),
                    "Paciente eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();

            // Ajustar índice si es necesario
            if (!pacientes.isEmpty()) {
                if (index >= pacientes.size()) index = pacientes.size() - 1;
                mostrarPacienteIndex(index);
            } else {
                mostrarPacienteVacio();
            }
        });
    }

    private void mostrarPacienteIndex(int i) {
        if (pacientes.isEmpty()) {
            mostrarPacienteVacio();
            return;
        }

        if (i < 0) i = 0;
        if (i >= pacientes.size()) i = pacientes.size() - 1;
        index = i;

        Paciente p = pacientes.get(index);
        tvNombre.setText("Nombre: " + p.getNombre());
        tvEdad.setText("Edad: " + p.getEdad());
        tvEmail.setText("Email: " + p.getEmail());
        tvDiagnostico.setText("Diagnóstico: " + p.getDiagnostico());

        btnAnterior.setEnabled(index > 0);
        btnAnterior.setColorFilter(getColor(index > 0 ? R.color.rojo : R.color.gris));

        btnSiguiente.setEnabled(index < pacientes.size() - 1);
        btnSiguiente.setColorFilter(getColor(index < pacientes.size() - 1 ? R.color.rojo : R.color.gris));

        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void mostrarPacienteVacio() {
        tvNombre.setText("No hay pacientes");
        tvEdad.setText("");
        tvEmail.setText("");
        tvDiagnostico.setText("");

        btnAnterior.setEnabled(false);
        btnSiguiente.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void mostrarDialogoPaciente(boolean modoAgregar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final android.view.View view = inflater.inflate(R.layout.dialog_paciente, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText etNombre = view.findViewById(R.id.etNombrePaciente);
        EditText etEdad = view.findViewById(R.id.etEdadPaciente);
        EditText etEmail = view.findViewById(R.id.etEmailPaciente);
        EditText etDiagnostico = view.findViewById(R.id.etDiagnosticoPaciente);
        Button btnGuardar = view.findViewById(R.id.btnGuardarPaciente);
        Button btnSalir = view.findViewById(R.id.btnSalirPaciente);

        Paciente paciente = null;
        if (!modoAgregar && !pacientes.isEmpty()) {
            paciente = pacientes.get(index);
            etNombre.setText(paciente.getNombre());
            etEdad.setText(String.valueOf(paciente.getEdad()));
            etEmail.setText(paciente.getEmail());
            etDiagnostico.setText(paciente.getDiagnostico());
        }

        Paciente finalPaciente = paciente;

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String diagnostico = etDiagnostico.getText().toString().trim();

            if (nombre.isEmpty()) { etNombre.setError("Complete este campo"); return; }
            if (edadStr.isEmpty()) { etEdad.setError("Complete este campo"); return; }
            if (email.isEmpty()) { etEmail.setError("Complete este campo"); return; }
            if (diagnostico.isEmpty()) { etDiagnostico.setError("Complete este campo"); return; }

            int edad = Integer.parseInt(edadStr);

            if (modoAgregar) {
                repo.insertarPaciente(new Paciente(nombre, edad, email, diagnostico));
            } else {
                finalPaciente.setNombre(nombre);
                finalPaciente.setEdad(edad);
                finalPaciente.setEmail(email);
                finalPaciente.setDiagnostico(diagnostico);
                repo.actualizarPaciente(finalPaciente);
            }

            dialog.dismiss();
            Snackbar.make(findViewById(android.R.id.content),
                    modoAgregar ? "Paciente añadido correctamente" : "Paciente modificado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });

        btnSalir.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
