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

        // Observar médicos desde la BD
        repo.obtenerMedicos().observe(this, lista -> {
            medicos = (lista != null) ? lista : new ArrayList<>();
            index = 0;
            mostrarMedicoIndex(index);
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> mostrarMedicoIndex(index - 1));
        btnSiguiente.setOnClickListener(v -> mostrarMedicoIndex(index + 1));

        // CRUD
        btnAgregar.setOnClickListener(v -> mostrarDialogoMedico(true));
        btnModificar.setOnClickListener(v -> {
            if (!medicos.isEmpty()) mostrarDialogoMedico(false);
        });
        btnEliminar.setOnClickListener(v -> {
            if (medicos.isEmpty()) return;

            repo.eliminarMedico(medicos.get(index));
            Snackbar.make(findViewById(android.R.id.content),
                    "Médico eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();

            if (!medicos.isEmpty()) {
                if (index >= medicos.size()) index = medicos.size() - 1;
                mostrarMedicoIndex(index);
            } else {
                mostrarMedicoVacio();
            }
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void mostrarMedicoIndex(int i) {
        if (medicos.isEmpty()) {
            mostrarMedicoVacio();
            return;
        }

        if (i < 0) i = 0;
        if (i >= medicos.size()) i = medicos.size() - 1;
        index = i;

        Medico m = medicos.get(index);
        tvNombre.setText("Nombre: " + m.getNombre());
        tvMatricula.setText("Matrícula: " + m.getMatricula());
        tvEspecialidad.setText("Especialidad: " + m.getEspecialidad());
        tvEmail.setText("Email: " + m.getEmail());

        btnAnterior.setEnabled(index > 0);
        btnAnterior.setColorFilter(getColor(index > 0 ? R.color.rojo : R.color.gris));

        btnSiguiente.setEnabled(index < medicos.size() - 1);
        btnSiguiente.setColorFilter(getColor(index < medicos.size() - 1 ? R.color.rojo : R.color.gris));

        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void mostrarMedicoVacio() {
        tvNombre.setText("No hay médicos");
        tvMatricula.setText("");
        tvEspecialidad.setText("");
        tvEmail.setText("");

        btnAnterior.setEnabled(false);
        btnAnterior.setColorFilter(getColor(R.color.gris));

        btnSiguiente.setEnabled(false);
        btnSiguiente.setColorFilter(getColor(R.color.gris));

        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
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

            if (nombre.isEmpty()) { etNombre.setError("Completa este campo"); return; }
            if (matricula.isEmpty()) { etMatricula.setError("Completa este campo"); return; }
            if (especialidad.isEmpty()) { etEspecialidad.setError("Completa este campo"); return; }
            if (email.isEmpty()) { etEmail.setError("Completa este campo"); return; }

            if (modoAgregar) {
                repo.insertarMedico(new Medico(nombre, matricula, especialidad, email));
            } else {
                Medico actual = medicos.get(index);
                actual.setNombre(nombre);
                actual.setMatricula(matricula);
                actual.setEspecialidad(especialidad);
                actual.setEmail(email);
                repo.actualizarMedico(actual);
            }

            dialog.dismiss();
            Snackbar.make(findViewById(android.R.id.content),
                    modoAgregar ? "Médico añadido" : "Médico modificado",
                    Snackbar.LENGTH_SHORT).show();
        });

        btnSalir.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
