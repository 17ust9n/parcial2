package com.example.parcial2.ui.medico;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Medico;
import com.example.parcial2.utils.Validaciones;

public class NuevoMedicoActivity extends AppCompatActivity {

    private EditText nombreEdit, especialidadEdit, telefonoEdit, emailEdit;
    private Button btnGuardar;
    private ClinicaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_medico);

        repo = new ClinicaRepository(getApplication());

        nombreEdit = findViewById(R.id.nombre);
        especialidadEdit = findViewById(R.id.especialidad);
        telefonoEdit = findViewById(R.id.telefono);
        emailEdit = findViewById(R.id.email); // Nuevo EditText para email
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarMedico());
    }

    private void guardarMedico() {
        if (Validaciones.validarCamposVacios(nombreEdit, especialidadEdit, emailEdit)) return;

        if (!telefonoEdit.getText().toString().isEmpty() &&
                Validaciones.telefonoInvalido(telefonoEdit)) return;

        Medico medico = new Medico(
                nombreEdit.getText().toString(),
                especialidadEdit.getText().toString(),
                telefonoEdit.getText().toString(),
                emailEdit.getText().toString() // Nuevo argumento
        );

        repo.insertarMedico(medico);
        Toast.makeText(this, "Médico guardado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
