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

        // Observer de Room (LA VERDAD de la BD)
        repo.obtenerMedicos().observe(this, lista -> {
            medicos = lista != null ? lista : new ArrayList<>();

            if (medicos.isEmpty()) {
                mostrarMedicoVacio();
                agregarMedicosPrueba(); // Room los insertará y el observer se disparará de nuevo
                return;
            }

            // Ajustar índice por seguridad
            if (index >= medicos.size()) index = medicos.size() - 1;
            if (index < 0) index = 0;

            mostrarMedico(index);
        });

        // Flechas
        btnAnterior.setOnClickListener(v -> mostrarMedico(index - 1));
        btnSiguiente.setOnClickListener(v -> mostrarMedico(index + 1));

        // CRUD
        btnAgregar.setOnClickListener(v -> mostrarDialogoMedico(true));
        btnModificar.setOnClickListener(v -> {
            if (!medicos.isEmpty()) mostrarDialogoMedico(false);
        });

        btnEliminar.setOnClickListener(v -> {
            if (medicos.isEmpty()) return;

            Medico m = medicos.get(index);
            repo.eliminarMedico(m);

            Snackbar.make(findViewById(android.R.id.content),
                    "Médico eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void mostrarMedico(int i) {
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

        // ==========================
        //      FLECHA IZQUIERDA
        // ==========================
        if (index > 0) {
            btnAnterior.setEnabled(true);
            btnAnterior.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnAnterior.setEnabled(false);
            btnAnterior.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        // ==========================
        //      FLECHA DERECHA
        // ==========================
        if (index < medicos.size() - 1) {
            btnSiguiente.setEnabled(true);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnSiguiente.setEnabled(false);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void mostrarMedicoVacio() {
        tvNombre.setText("No hay médicos");
        tvMatricula.setText("");
        tvEspecialidad.setText("");
        tvEmail.setText("");

        btnAnterior.setEnabled(false);
        btnAnterior.setColorFilter(getResources().getColor(R.color.gris, null));

        btnSiguiente.setEnabled(false);
        btnSiguiente.setColorFilter(getResources().getColor(R.color.gris, null));

        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void agregarMedicosPrueba() {
        repo.insertarMedico(new Medico("Dra. Ana Gómez", "MAT456", "Pediatría", "ana@correo.com"));
        repo.insertarMedico(new Medico("Dr. Juan Pérez", "MAT123", "Cardiología", "juan@correo.com"));
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
                if (nombre.isEmpty()) etNombre.setError("Completa este campo");
                if (matricula.isEmpty()) etMatricula.setError("Completa este campo");
                if (especialidad.isEmpty()) etEspecialidad.setError("Completa este campo");
                if (email.isEmpty()) etEmail.setError("Completa este campo");
                return;
            }

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
