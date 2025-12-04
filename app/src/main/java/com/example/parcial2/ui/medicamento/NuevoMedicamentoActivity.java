package com.example.parcial2.ui.medicamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial2.R;
import com.example.parcial2.data.repository.ClinicaRepository;
import com.example.parcial2.model.Medicamento;
import com.example.parcial2.utils.Validaciones;

public class NuevoMedicamentoActivity extends AppCompatActivity {

    private EditText etNombre, etUso, etLaboratorio, etDosis,
            etEfectos, etPrecio, etVencimiento;
    private Button btnGuardar;

    private ClinicaRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_medicamento);

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas
        etNombre = findViewById(R.id.etNombreNuevoMed);
        etUso = findViewById(R.id.etUsoNuevoMed);
        etLaboratorio = findViewById(R.id.etLaboratorioNuevoMed);
        etDosis = findViewById(R.id.etDosisNuevoMed);
        etEfectos = findViewById(R.id.etEfectosNuevoMed);
        etPrecio = findViewById(R.id.etPrecioNuevoMed);
        etVencimiento = findViewById(R.id.etVencimientoNuevoMed);

        btnGuardar = findViewById(R.id.btnGuardarNuevoMed);

        btnGuardar.setOnClickListener(v -> guardarMedicamento());
    }

    private void guardarMedicamento() {

        // Validación de campos vacíos
        if (Validaciones.validarCamposVacios(
                etNombre, etUso, etLaboratorio, etDosis,
                etEfectos, etPrecio, etVencimiento
        )) return;

        // Crear objeto medicamento
        Medicamento medicamento = new Medicamento(
                etNombre.getText().toString(),
                etUso.getText().toString(),
                etLaboratorio.getText().toString(),
                etDosis.getText().toString(),
                etEfectos.getText().toString(),
                etPrecio.getText().toString(),
                etVencimiento.getText().toString()
        );

        repo.insertarMedicamento(medicamento);

        Toast.makeText(this, "Medicamento guardado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
