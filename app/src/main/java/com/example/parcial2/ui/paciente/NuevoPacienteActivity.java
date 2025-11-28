package com.example.parcial2.ui.paciente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.utils.Validaciones;

public class NuevoPacienteActivity extends AppCompatActivity {

    private EditText nombreEdit, edadEdit, diagnosticoEdit;
    private Button btnGuardar;
    private ClinicaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_paciente);

        repo = new ClinicaRepository(getApplication());

        nombreEdit = findViewById(R.id.nombre);
        edadEdit = findViewById(R.id.edad);
        diagnosticoEdit = findViewById(R.id.diagnostico);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarPaciente());
    }

    private void guardarPaciente() {
        if (Validaciones.validarCamposVacios(nombreEdit, edadEdit)) return;
        if (Validaciones.edadInvalida(edadEdit)) return;

        // Suponiendo que agregaste un campo EditText para el email
        EditText emailEdit = findViewById(R.id.email);

        Paciente paciente = new Paciente(
                nombreEdit.getText().toString(),
                Integer.parseInt(edadEdit.getText().toString()),
                emailEdit.getText().toString(),         // <-- email
                diagnosticoEdit.getText().toString()    // <-- diagnóstico
        );

        repo.insertarPaciente(paciente);
        Toast.makeText(this, "Paciente guardado", Toast.LENGTH_SHORT).show();
        finish();
    }

}
