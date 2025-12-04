package com.example.parcial2.ui.medicamento;

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
import com.example.parcial2.model.Medicamento;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicamentosActivity extends AppCompatActivity {

    private TextView tvNombre, tvUso, tvLaboratorio, tvDosis,
            tvEfectos, tvPrecio, tvVencimiento;
    private ImageButton btnAnterior, btnSiguiente;
    private Button btnAgregar, btnModificar, btnEliminar, btnVolver;

    private ClinicaRepository repo;
    private List<Medicamento> medicamentos = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        repo = new ClinicaRepository(getApplication());

        // Vincular vistas
        tvNombre = findViewById(R.id.tvNombreMedicamento);
        tvUso = findViewById(R.id.tvUsoMedicamento);
        tvLaboratorio = findViewById(R.id.tvLaboratorioMedicamento);
        tvDosis = findViewById(R.id.tvDosisMedicamento);
        tvEfectos = findViewById(R.id.tvEfectosMedicamento);
        tvPrecio = findViewById(R.id.tvPrecioMedicamento);
        tvVencimiento = findViewById(R.id.tvVencimientoMedicamento);

        btnAnterior = findViewById(R.id.btnAnteriorMedicamento);
        btnSiguiente = findViewById(R.id.btnSiguienteMedicamento);

        btnAgregar = findViewById(R.id.btnAgregarMedicamento);
        btnModificar = findViewById(R.id.btnModificarMedicamento);
        btnEliminar = findViewById(R.id.btnEliminarMedicamento);
        btnVolver = findViewById(R.id.btnVolverMedicamentos);

        // Cargar medicamentos y observar cambios
        repo.obtenerMedicamentos().observe(this, lista -> {
            if (lista == null || lista.isEmpty()) {
                agregarMedicamentosPrueba();
            } else {
                medicamentos = lista;
                index = 0;
                mostrarMedicamento(index);
            }
        });

        // Navegación
        btnAnterior.setOnClickListener(v -> {
            if (!medicamentos.isEmpty() && index > 0) {
                index--;
                mostrarMedicamento(index);
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (!medicamentos.isEmpty() && index < medicamentos.size() - 1) {
                index++;
                mostrarMedicamento(index);
            }
        });

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());

        // Botones CRUD
        btnAgregar.setOnClickListener(v -> mostrarDialogoMedicamento(true));
        btnModificar.setOnClickListener(v -> {
            if (!medicamentos.isEmpty()) mostrarDialogoMedicamento(false);
        });
        btnEliminar.setOnClickListener(v -> {
            if (medicamentos.isEmpty()) return;

            Medicamento actual = medicamentos.get(index);
            repo.eliminarMedicamento(actual);
            medicamentos.remove(index);

            if (index >= medicamentos.size()) index = medicamentos.size() - 1;
            mostrarMedicamento(index);

            Snackbar.make(findViewById(android.R.id.content),
                    "Medicamento eliminado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });
    }

    private void mostrarMedicamento(int i) {
        if (medicamentos.isEmpty()) {
            tvNombre.setText("No hay medicamentos");
            tvUso.setText("");
            tvLaboratorio.setText("");
            tvDosis.setText("");
            tvEfectos.setText("");
            tvPrecio.setText("");
            tvVencimiento.setText("");
            return;
        }

        Medicamento m = medicamentos.get(i);
        tvNombre.setText(m.getNombre());
        tvUso.setText("Uso: " + m.getUso());
        tvLaboratorio.setText("Laboratorio: " + m.getLaboratorio());
        tvDosis.setText("Dosis: " + m.getDosis());
        tvEfectos.setText("Efectos: " + m.getEfectos());
        tvPrecio.setText("Precio: " + m.getPrecio());
        tvVencimiento.setText("Vencimiento: " + m.getVencimiento());
    }

    private void agregarMedicamentosPrueba() {
        List<Medicamento> prueba = new ArrayList<>();
        prueba.add(new Medicamento(
                "Paracetamol",
                "Alivia el dolor y baja la fiebre",
                "Bayer",
                "Tablestas de 500mg",
                "Náuseas, mareos",
                "$50",
                "2025-12-31"
        ));
        prueba.add(new Medicamento(
                "Ibuprofeno",
                "Analgésico y antinflamatorio",
                "Pfizer",
                "400mg en cápsula",
                "Dolor de estómago",
                "$70",
                "2025-11-30"
        ));

        for (Medicamento m : prueba) {
            repo.insertarMedicamento(m);
        }
    }

    private void mostrarDialogoMedicamento(boolean modoAgregar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final android.view.View view = inflater.inflate(R.layout.dialog_medicamento, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText etNombre = view.findViewById(R.id.etNombreMedicamento);
        EditText etUso = view.findViewById(R.id.etUsoMedicamento);
        EditText etLaboratorio = view.findViewById(R.id.etLaboratorioMedicamento);
        EditText etDosis = view.findViewById(R.id.etDosisMedicamento);
        EditText etEfectos = view.findViewById(R.id.etEfectosMedicamento);
        EditText etPrecio = view.findViewById(R.id.etPrecioMedicamento);
        EditText etVencimiento = view.findViewById(R.id.etVencimientoMedicamento);
        Button btnGuardar = view.findViewById(R.id.btnGuardarMedicamento);
        Button btnSalir = view.findViewById(R.id.btnSalirMedicamento);

        if (!modoAgregar && !medicamentos.isEmpty()) {
            Medicamento actual = medicamentos.get(index);
            etNombre.setText(actual.getNombre());
            etUso.setText(actual.getUso());
            etDosis.setText(actual.getDosis());
            etEfectos.setText(actual.getEfectos());
            etPrecio.setText(actual.getPrecio());
            etVencimiento.setText(actual.getVencimiento());
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String uso = etUso.getText().toString().trim();
            String laboratorio = etLaboratorio.getText().toString().trim();
            String dosis = etDosis.getText().toString().trim();
            String efectos = etEfectos.getText().toString().trim();
            String precio = etPrecio.getText().toString().trim();
            String vencimiento = etVencimiento.getText().toString().trim();

            if (nombre.isEmpty() || uso.isEmpty() || laboratorio.isEmpty() || dosis.isEmpty() || efectos.isEmpty() || precio.isEmpty() || vencimiento.isEmpty()) {
                if (nombre.isEmpty()) etNombre.setError("Campo obligatorio");
                if (uso.isEmpty()) etUso.setError("Campo obligatorio");
                if (laboratorio.isEmpty()) etLaboratorio.setError("Campo obligatorio");
                if (dosis.isEmpty()) etDosis.setError("Campo obligatorio");
                if (efectos.isEmpty()) etEfectos.setError("Campo obligatorio");
                if (precio.isEmpty()) etPrecio.setError("Campo obligatorio");
                if (vencimiento.isEmpty()) etVencimiento.setError("Campo obligatorio");
                return;
            }

            if (modoAgregar) {
                Medicamento nuevo = new Medicamento(nombre, uso,
                        laboratorio, dosis, efectos, precio, vencimiento);
                repo.insertarMedicamento(nuevo);
                medicamentos.add(nuevo);
                index = medicamentos.size() - 1;
            } else {
                Medicamento actual = medicamentos.get(index);
                actual.setNombre(nombre);
                actual.setUso(uso);
                actual.setLaboratorio(laboratorio);
                actual.setDosis(dosis);
                actual.setEfectos(efectos);
                actual.setPrecio(precio);
                actual.setVencimiento(vencimiento);
                repo.actualizarMedicamento(actual);
            }

            mostrarMedicamento(index);
            dialog.dismiss();

            Snackbar.make(findViewById(android.R.id.content),
                    modoAgregar ? "Medicamento añadido correctamente" : "Medicamento modificado correctamente",
                    Snackbar.LENGTH_SHORT).show();
        });

        btnSalir.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
