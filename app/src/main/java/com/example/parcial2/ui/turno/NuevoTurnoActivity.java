package com.example.parcial2.ui.turno;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Medico;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.model.Medicamento;
import com.example.parcial2.model.Turno;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NuevoTurnoActivity extends AppCompatActivity {

    private TextView tvMedicoSeleccionado, tvPacienteSeleccionado, tvMedicamentoSeleccionado;
    private EditText etFecha, etHora, etConsultorio;
    private ImageButton btnAnteriorMedico, btnSiguienteMedico, btnAnteriorPaciente, btnSiguientePaciente, btnAnteriorMedicamento, btnSiguienteMedicamento;
    private Button btnGuardarTurno, btnCancelar;

    private ClinicaRepository repo;

    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Medicamento> medicamentos;
    private int indexMedicoSeleccionado = 0; // Controlar el índice del médico seleccionado
    private int indexPacienteSeleccionado = 0; // Controlar el índice del paciente seleccionado
    private int indexMedicamentoSeleccionado = 0; // Controlar el índice del medicamento seleccionado

    private Paciente pacienteSeleccionado;
    private Medicamento medicamentoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_turno); // Asegúrate de que este sea el layout correcto

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas desde el XML
        tvMedicoSeleccionado = findViewById(R.id.tvMedicoSeleccionado);
        tvPacienteSeleccionado = findViewById(R.id.tvPacienteSeleccionado);
        tvMedicamentoSeleccionado = findViewById(R.id.tvMedicamentoSeleccionado);

        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        etConsultorio = findViewById(R.id.etConsultorio);

        btnAnteriorMedico = findViewById(R.id.btnAnteriorMedico);
        btnSiguienteMedico = findViewById(R.id.btnSiguienteMedico);
        btnAnteriorPaciente = findViewById(R.id.btnAnteriorPaciente);
        btnSiguientePaciente = findViewById(R.id.btnSiguientePaciente);
        btnAnteriorMedicamento = findViewById(R.id.btnAnteriorMedicamento);
        btnSiguienteMedicamento = findViewById(R.id.btnSiguienteMedicamento);
        btnGuardarTurno = findViewById(R.id.btnGuardarTurno);
        btnCancelar = findViewById(R.id.btnCancelarTurno);

        // Cargar listas de médicos, pacientes y medicamentos
        repo.obtenerMedicos().observe(this, this::cargarMedicos);
        repo.obtenerPacientes().observe(this, this::cargarPacientes);
        repo.obtenerMedicamentos().observe(this, this::cargarMedicamentos);

        // Flechas de navegación para Médicos
        btnAnteriorMedico.setOnClickListener(v -> mostrarMedicoAnterior());
        btnSiguienteMedico.setOnClickListener(v -> mostrarMedicoSiguiente());

        // Flechas de navegación para Pacientes
        btnAnteriorPaciente.setOnClickListener(v -> mostrarPacienteAnterior());
        btnSiguientePaciente.setOnClickListener(v -> mostrarPacienteSiguiente());

        // Flechas de navegación para Medicamentos
        btnAnteriorMedicamento.setOnClickListener(v -> mostrarMedicamentoAnterior());
        btnSiguienteMedicamento.setOnClickListener(v -> mostrarMedicamentoSiguiente());

        // Guardar Turno
        btnGuardarTurno.setOnClickListener(v -> guardarTurno());

        // Cancelar Turno
        btnCancelar.setOnClickListener(v -> finish());
    }

    /* ===================== CARGAR MÉDICOS, PACIENTES Y MEDICAMENTOS ===================== */

    private void cargarMedicos(List<Medico> medicos) {
        if (medicos == null || medicos.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No hay médicos disponibles",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        this.medicos = medicos;
        mostrarMedicoActual();
    }

    private void cargarPacientes(List<Paciente> pacientes) {
        if (pacientes == null || pacientes.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No hay pacientes disponibles",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        this.pacientes = pacientes;
        mostrarPacienteActual();
    }

    private void cargarMedicamentos(List<Medicamento> medicamentos) {
        if (medicamentos == null || medicamentos.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content),
                    "No hay medicamentos disponibles",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        this.medicamentos = medicamentos;
        mostrarMedicamentoActual();
    }

    /* ===================== NAVEGACIÓN ENTRE MÉDICOS ===================== */

    private void mostrarMedicoActual() {
        if (medicos != null && !medicos.isEmpty()) {
            Medico medico = medicos.get(indexMedicoSeleccionado);
            tvMedicoSeleccionado.setText(medico.getNombre());
        }
    }

    private void mostrarMedicoAnterior() {
        if (medicos != null && !medicos.isEmpty()) {
            indexMedicoSeleccionado--;
            if (indexMedicoSeleccionado < 0) {
                indexMedicoSeleccionado = medicos.size() - 1; // Volver al último médico
            }
            mostrarMedicoActual();
        }
    }

    private void mostrarMedicoSiguiente() {
        if (medicos != null && !medicos.isEmpty()) {
            indexMedicoSeleccionado++;
            if (indexMedicoSeleccionado >= medicos.size()) {
                indexMedicoSeleccionado = 0; // Volver al primer médico
            }
            mostrarMedicoActual();
        }
    }

    /* ===================== NAVEGACIÓN ENTRE PACIENTES ===================== */

    private void mostrarPacienteActual() {
        if (pacientes != null && !pacientes.isEmpty()) {
            Paciente paciente = pacientes.get(indexPacienteSeleccionado);
            tvPacienteSeleccionado.setText(paciente.getNombre());
        }
    }

    private void mostrarPacienteAnterior() {
        if (pacientes != null && !pacientes.isEmpty()) {
            indexPacienteSeleccionado--;
            if (indexPacienteSeleccionado < 0) {
                indexPacienteSeleccionado = pacientes.size() - 1; // Volver al último paciente
            }
            mostrarPacienteActual();
        }
    }

    private void mostrarPacienteSiguiente() {
        if (pacientes != null && !pacientes.isEmpty()) {
            indexPacienteSeleccionado++;
            if (indexPacienteSeleccionado >= pacientes.size()) {
                indexPacienteSeleccionado = 0; // Volver al primer paciente
            }
            mostrarPacienteActual();
        }
    }

    /* ===================== NAVEGACIÓN ENTRE MEDICAMENTOS ===================== */

    private void mostrarMedicamentoActual() {
        if (medicamentos != null && !medicamentos.isEmpty()) {
            Medicamento medicamento = medicamentos.get(indexMedicamentoSeleccionado);
            tvMedicamentoSeleccionado.setText(medicamento.getNombre());
        }
    }

    private void mostrarMedicamentoAnterior() {
        if (medicamentos != null && !medicamentos.isEmpty()) {
            indexMedicamentoSeleccionado--;
            if (indexMedicamentoSeleccionado < 0) {
                indexMedicamentoSeleccionado = medicamentos.size() - 1; // Volver al último medicamento
            }
            mostrarMedicamentoActual();
        }
    }

    private void mostrarMedicamentoSiguiente() {
        if (medicamentos != null && !medicamentos.isEmpty()) {
            indexMedicamentoSeleccionado++;
            if (indexMedicamentoSeleccionado >= medicamentos.size()) {
                indexMedicamentoSeleccionado = 0; // Volver al primer medicamento
            }
            mostrarMedicamentoActual();
        }
    }

    /* ===================== GUARDAR TURNO ===================== */

    private void guardarTurno() {
        String fecha = etFecha.getText().toString().trim();
        String hora = etHora.getText().toString().trim();
        String consultorio = etConsultorio.getText().toString().trim();

        // Validaciones solo para campos de fecha, hora y consultorio
        if (fecha.isEmpty()) {
            etFecha.setError("Ingrese fecha (dd/mm/aaaa)");
            return;
        }

        if (hora.isEmpty()) {
            etHora.setError("Ingrese hora (hh:mm)");
            return;
        }

        if (consultorio.isEmpty()) {
            etConsultorio.setError("Ingrese consultorio");
            return;
        }

        // Crear el turno usando directamente los TextViews
        Turno nuevoTurno = new Turno(
                tvMedicoSeleccionado.getText().toString(),
                tvPacienteSeleccionado.getText().toString(),
                tvMedicamentoSeleccionado.getText().toString(),
                fecha,
                hora,
                consultorio
        );

        // Guardar el turno en la base de datos
        repo.insertarTurno(nuevoTurno, exitoso -> {
            if (exitoso) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Turno creado exitosamente",
                        Snackbar.LENGTH_SHORT).show();
                finish();
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        "Error al crear el turno",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }



    private void mostrarError(String mensaje) {
        Snackbar.make(findViewById(android.R.id.content),
                mensaje,
                Snackbar.LENGTH_SHORT).show();
    }
}
