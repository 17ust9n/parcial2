package com.example.parcial2.ui.turno;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Turno;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListaTurnosActivity extends AppCompatActivity {

    private TextView tvInfoMedico, tvInfoPaciente, tvInfoMedicamento, tvFechaHora;
    private ImageButton btnAnterior, btnSiguiente;
    private Button btnAgregarTurno, btnModificarTurno, btnEliminarTurno, btnVolver;

    private ClinicaRepository repo;
    private List<Turno> turnos = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas
        tvInfoMedico = findViewById(R.id.tvInfoMedico);
        tvInfoPaciente = findViewById(R.id.tvInfoPaciente);
        tvInfoMedicamento = findViewById(R.id.tvInfoMedicamento);
        tvFechaHora = findViewById(R.id.tvFechaHora);

        btnAnterior = findViewById(R.id.btnAnteriorTurno);
        btnSiguiente = findViewById(R.id.btnSiguienteTurno);

        btnAgregarTurno = findViewById(R.id.btnAgregarTurno);
        btnModificarTurno = findViewById(R.id.btnModificarTurno);
        btnEliminarTurno = findViewById(R.id.btnEliminarTurno);
        btnVolver = findViewById(R.id.btnVolverTurnos);

        // Observar turnos
        repo.obtenerTurnos().observe(this, lista -> {
            turnos = lista != null ? lista : new ArrayList<>();

            if (turnos.isEmpty()) {
                mostrarTurnoVacio();
                return;
            }

            if (index >= turnos.size()) index = turnos.size() - 1;
            if (index < 0) index = 0;

            mostrarTurno(index);
        });

        // Navegación entre turnos
        btnAnterior.setOnClickListener(v -> mostrarTurno(index - 1));
        btnSiguiente.setOnClickListener(v -> mostrarTurno(index + 1));

        // Agregar nuevo turno
        btnAgregarTurno.setOnClickListener(v -> {
            Intent intent = new Intent(this, NuevoTurnoActivity.class);
            startActivity(intent);
        });

        // Modificar turno (si tienes actividad de edición)
        btnModificarTurno.setOnClickListener(v -> {
            if (turnos.isEmpty()) return;

            Turno turnoActual = turnos.get(index);
            // Por ejemplo, enviar ID del turno a una actividad de edición
            Intent intent = new Intent(this, NuevoTurnoActivity.class); // Cambiar a EditarTurnoActivity si la tienes
            intent.putExtra("ID_TURNO", turnoActual.getId());
            startActivity(intent);
        });

        // Eliminar turno
        btnEliminarTurno.setOnClickListener(v -> {
            if (turnos.isEmpty()) return;

            new AlertDialog.Builder(this)
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Está seguro de eliminar este turno?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        repo.eliminarTurno(turnos.get(index));
                        Snackbar.make(findViewById(android.R.id.content),
                                "Turno eliminado correctamente",
                                Snackbar.LENGTH_SHORT).show();
                        // Actualizar lista
                        if (!turnos.isEmpty()) mostrarTurno(index);
                        else mostrarTurnoVacio();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        // Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    private void mostrarTurno(int i) {
        if (turnos.isEmpty()) {
            mostrarTurnoVacio();
            return;
        }

        if (i < 0) i = 0;
        if (i >= turnos.size()) i = turnos.size() - 1;
        index = i;

        Turno t = turnos.get(index);

        tvInfoMedico.setText("Médico: " + t.getNombreMedico());
        tvInfoPaciente.setText("Paciente: " + t.getNombrePaciente());
        tvInfoMedicamento.setText("Medicamento: " + t.getNombreMedicamento());
        tvFechaHora.setText("Fecha y hora: " + t.getFecha() + t.getHora() + "\nConsultorio: " + t.getConsultorio());

        // Flecha izquierda
        if (index > 0) {
            btnAnterior.setEnabled(true);
            btnAnterior.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnAnterior.setEnabled(false);
            btnAnterior.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        // Flecha derecha
        if (index < turnos.size() - 1) {
            btnSiguiente.setEnabled(true);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.rojo, null));
        } else {
            btnSiguiente.setEnabled(false);
            btnSiguiente.setColorFilter(getResources().getColor(R.color.gris, null));
        }

        btnEliminarTurno.setEnabled(true);
        btnModificarTurno.setEnabled(true);
    }

    private void mostrarTurnoVacio() {
        tvInfoMedico.setText("No hay turnos registrados");
        tvInfoPaciente.setText("");
        tvInfoMedicamento.setText("");
        tvFechaHora.setText("");

        btnAnterior.setEnabled(false);
        btnAnterior.setColorFilter(getResources().getColor(R.color.gris, null));

        btnSiguiente.setEnabled(false);
        btnSiguiente.setColorFilter(getResources().getColor(R.color.gris, null));

        btnEliminarTurno.setEnabled(false);
        btnModificarTurno.setEnabled(false);
    }
}
