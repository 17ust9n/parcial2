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

        // Cargar pacientes desde repositorio o agregar de prueba
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

        // Agregar
        btnAgregar.setOnClickListener(v -> mostrarDialogoPaciente(true));

        // Modificar
        btnModificar.setOnClickListener(v -> {
            if (!pacientes.isEmpty()) mostrarDialogoPaciente(false);
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (pacientes.isEmpty()) return;

            Paciente actual = pacientes.get(index);
            repo.eliminarPaciente(actual);
            pacientes.remove(index);

            if (index > 0) index--;
            mostrarPaciente(index);

            Snackbar.make(findViewById(android.R.id.content),
                    "Usuario eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });
    }

    private void mostrarPaciente(int i) {
        if (pacientes.isEmpty()) {
            tvNombre.setText("No hay pacientes");
            tvEdad.setText("");
            tvEmail.setText("");
            tvDiagnostico.setText("");
            return;
        }

        Paciente p = pacientes.get(i);
        tvNombre.setText("Nombre: " + p.getNombre());
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

        // Si es modificar, cargar datos actuales
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

            boolean hayError = false;

            if (nombre.isEmpty()) {
                etNombre.setError("Por favor, complete este campo");
                hayError = true;
            }

            if (edadStr.isEmpty()) {
                etEdad.setError("Por favor, complete este campo");
                hayError = true;
            }

            if (email.isEmpty()) {
                etEmail.setError("Por favor, complete este campo");
                hayError = true;
            }

            if (diagnostico.isEmpty()) {
                etDiagnostico.setError("Por favor, complete este campo");
                hayError = true;
            }

            if (hayError) {
                return; // No guardamos mientras haya campos vacíos
            }

            int edad = Integer.parseInt(edadStr);

            if (modoAgregar) {
                Paciente nuevo = new Paciente(nombre, edad, email, diagnostico);
                repo.insertarPaciente(nuevo);
                pacientes.add(nuevo);
                index = pacientes.size() - 1;
            } else {
                Paciente actual = pacientes.get(index);
                actual.setNombre(nombre);
                actual.setEdad(edad);
                actual.setEmail(email);
                actual.setDiagnostico(diagnostico);
                repo.actualizarPaciente(actual);
            }

            mostrarPaciente(index);
            dialog.dismiss();

            Snackbar.make(findViewById(android.R.id.content),
                    modoAgregar ? "Usuario añadido correctamente" : "Usuario modificado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });


        btnSalir.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
