package com.example.parcial2.ui.medico;

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
import com.example.parcial2.model.Medico;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicosActivity extends AppCompatActivity {

    private TextView tvNombre, tvMatricula, tvEspecialidad, tvEmail;
    private ImageButton btnAnterior, btnSiguiente;
    private Button btnAgregar, btnModificar, btnEliminar, btnVolver;

    private ClinicaRepository repo;
    private List<Medico> medicos = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicos);

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas
        tvNombre = findViewById(R.id.tvNombreMedico);
        tvMatricula = findViewById(R.id.tvMatriculaMedico);
        tvEspecialidad = findViewById(R.id.tvEspecialidadMedico);
        tvEmail = findViewById(R.id.tvEmailMedico);

        btnAnterior = findViewById(R.id.btnAnteriorMedico);
        btnSiguiente = findViewById(R.id.btnSiguienteMedico);

        btnAgregar = findViewById(R.id.btnAgregarMedico);
        btnModificar = findViewById(R.id.btnModificarMedico);
        btnEliminar = findViewById(R.id.btnEliminarMedico);
        btnVolver = findViewById(R.id.btnVolverMedicos);

        // Cargar médicos y observar cambios
        repo.obtenerMedicos().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                agregarMedicosPrueba();
            } else {
                medicos = lista;
                index = 0;
                mostrarMedico(index);
            }
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> {
            if (!medicos.isEmpty() && index > 0) {
                index--;
                mostrarMedico(index);
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (!medicos.isEmpty() && index < medicos.size() - 1) {
                index++;
                mostrarMedico(index);
            }
        });

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());

        // Botones CRUD
        btnAgregar.setOnClickListener(v -> mostrarDialogoMedico(true));
        btnModificar.setOnClickListener(v -> {
            if (!medicos.isEmpty()) mostrarDialogoMedico(false);
        });
        btnEliminar.setOnClickListener(v -> {
            if (medicos.isEmpty()) return;

            Medico actual = medicos.get(index);
            repo.eliminarMedico(actual);
            medicos.remove(index);

            if (index >= medicos.size()) index = medicos.size() - 1;
            mostrarMedico(index);

            Snackbar.make(findViewById(android.R.id.content),
                    "Médico eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });
    }

    private void mostrarMedico(int i) {
        if (medicos.isEmpty()) {
            tvNombre.setText("No hay médicos");
            tvMatricula.setText("");
            tvEspecialidad.setText("");
            tvEmail.setText("");
            return;
        }

        Medico m = medicos.get(i);
        tvNombre.setText(m.getNombre());
        tvMatricula.setText("Matrícula: " + m.getMatricula());
        tvEspecialidad.setText("Especialidad: " + m.getEspecialidad());
        tvEmail.setText("Email: " + m.getEmail());
    }

    private void agregarMedicosPrueba() {
        List<Medico> prueba = new ArrayList<>();
        prueba.add(new Medico("Dr. Juan Pérez", "MAT123", "Cardiología", "juan@correo.com"));
        prueba.add(new Medico("Dra. Ana Gómez", "MAT456", "Pediatría", "ana@correo.com"));

        for (Medico m : prueba) {
            repo.insertarMedico(m);
        }
    }

    private void mostrarDialogoMedico(boolean modoAgregar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final android.view.View view = inflater.inflate(R.layout.dialog_medico, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText etNombre = view.findViewById(R.id.etNombreMedico);
        EditText etMatricula = view.findViewById(R.id.etMatriculaMedico);
        EditText etEspecialidad = view.findViewById(R.id.etEspecialidadMedico);
        EditText etEmail = view.findViewById(R.id.etEmailMedico);
        Button btnGuardar = view.findViewById(R.id.btnGuardarMedico);
        Button btnSalir = view.findViewById(R.id.btnSalirMedico);

        if (!modoAgregar && !medicos.isEmpty()) {
            Medico actual = medicos.get(index);
            etNombre.setText(actual.getNombre());
            etMatricula.setText(actual.getMatricula());
            etEspecialidad.setText(actual.getEspecialidad());
            etEmail.setText(actual.getEmail());
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String matricula = etMatricula.getText().toString().trim();
            String especialidad = etEspecialidad.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nombre.isEmpty() || matricula.isEmpty() || especialidad.isEmpty() || email.isEmpty()) {
                if (nombre.isEmpty()) etNombre.setError("Por favor, complete todos los campos");
                if (matricula.isEmpty()) etMatricula.setError("Por favor, complete todos los campos");
                if (especialidad.isEmpty()) etEspecialidad.setError("Por favor, complete todos los campos");
                if (email.isEmpty()) etEmail.setError("Por favor, complete todos los campos");
                return;
            }

            if (modoAgregar) {
                Medico nuevo = new Medico(nombre, matricula, especialidad, email);
                repo.insertarMedico(nuevo);
                medicos.add(nuevo);
                index = medicos.size() - 1;
            } else {
                Medico actual = medicos.get(index);
                actual.setNombre(nombre);
                actual.setMatricula(matricula);
                actual.setEspecialidad(especialidad);
                actual.setEmail(email);
                repo.actualizarMedico(actual);
            }

            mostrarMedico(index);
            dialog.dismiss();

            Snackbar.make(findViewById(android.R.id.content),
                    modoAgregar ? "Médico añadido correctamente" : "Médico modificado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });

        btnSalir.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
