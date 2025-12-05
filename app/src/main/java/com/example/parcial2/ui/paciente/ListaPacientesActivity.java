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

        // Limpiar BD y agregar pacientes de prueba
        repo.eliminarTodosPacientes();
        agregarPacientesPrueba();

        // Observar BD
        repo.obtenerPacientes().observe(this, lista -> {
            pacientes = lista;

            if (pacientes == null || pacientes.isEmpty()) {
                mostrarPacienteVacio();
                return;
            }

            if (index >= pacientes.size()) index = pacientes.size() - 1;
            if (index < 0) index = 0;

            mostrarPaciente(index);
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> {
            if (index > 0) {
                index--;
                mostrarPaciente(index);
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (index < pacientes.size() - 1) {
                index++;
                mostrarPaciente(index);
            }
        });

        btnVolver.setOnClickListener(v -> finish());

        btnAgregar.setOnClickListener(v -> mostrarDialogoPaciente(true));
        btnModificar.setOnClickListener(v -> {
            if (!pacientes.isEmpty()) mostrarDialogoPaciente(false);
        });

        btnEliminar.setOnClickListener(v -> {
            if (pacientes.isEmpty()) return;

            Paciente actual = pacientes.get(index);
            repo.eliminarPaciente(actual);

            Snackbar.make(findViewById(android.R.id.content),
                    "Paciente eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });
    }

    private void mostrarPaciente(int i) {
        if (pacientes.isEmpty()) {
            mostrarPacienteVacio();
            return;
        }

        Paciente p = pacientes.get(i);

        tvNombre.setText("Nombre: " + p.getNombre());
        tvEdad.setText("Edad: " + p.getEdad());
        tvEmail.setText("Email: " + p.getEmail());
        tvDiagnostico.setText("Diagnóstico: " + p.getDiagnostico());

        // Flecha anterior
        if (i > 0) {
            btnAnterior.setEnabled(true);
            btnAnterior.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnAnterior.setEnabled(false);
            btnAnterior.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        // Flecha siguiente
        if (i < pacientes.size() - 1) {
            btnSiguiente.setEnabled(true);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnSiguiente.setEnabled(false);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        // Botones modificar y eliminar
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

    private void agregarPacientesPrueba() {
        repo.insertarPaciente(new Paciente("Luis Martínez", 40, "luis@correo.com", "Dolor de cabeza"));
        repo.insertarPaciente(new Paciente("María López", 32, "maria@correo.com", "Alergia"));
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
        if (!modoAgregar) {
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

            boolean error = false;
            if (nombre.isEmpty()) { etNombre.setError("Complete este campo"); error = true; }
            if (edadStr.isEmpty()) { etEdad.setError("Complete este campo"); error = true; }
            if (email.isEmpty()) { etEmail.setError("Complete este campo"); error = true; }
            if (diagnostico.isEmpty()) { etDiagnostico.setError("Complete este campo"); error = true; }
            if (error) return;

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
