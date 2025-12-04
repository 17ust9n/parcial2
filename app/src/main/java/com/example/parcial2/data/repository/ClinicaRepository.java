package com.example.parcial2.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.parcial2.data.local.AppDatabase;
import com.example.parcial2.data.local.MedicoDao;
import com.example.parcial2.data.local.PacienteDao;
import com.example.parcial2.data.local.MedicamentoDao;
import com.example.parcial2.model.Medico;
import com.example.parcial2.model.Paciente;
import com.example.parcial2.model.Medicamento;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClinicaRepository {

    private final MedicoDao medicoDao;
    private final PacienteDao pacienteDao;
    private final MedicamentoDao medicamentoDao;
    private final FirebaseFirestore firestore;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ClinicaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        medicoDao = db.medicoDao();
        pacienteDao = db.pacienteDao();
        medicamentoDao = db.medicamentoDao();
        firestore = FirebaseFirestore.getInstance();
    }

    // ============================
    //        MÉDICOS
    // ============================
    public LiveData<List<Medico>> obtenerMedicos() {
        return medicoDao.allMedicos();
    }

    public void insertarMedico(Medico medico) {
        executor.execute(() -> {
            long id = medicoDao.insertar(medico);
            medico.setId((int) id);
            guardarMedicoEnFirestore(medico);
        });
    }

    public void actualizarMedico(Medico medico) {
        executor.execute(() -> {
            medicoDao.actualizar(medico);
            guardarMedicoEnFirestore(medico);
        });
    }

    public void eliminarMedico(Medico medico) {
        executor.execute(() -> {
            medicoDao.eliminar(medico);
            firestore.collection("medicos")
                    .document(String.valueOf(medico.getId()))
                    .delete();
        });
    }

    private void guardarMedicoEnFirestore(Medico medico) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", medico.getNombre());
        data.put("especialidad", medico.getEspecialidad());
        data.put("matricula", medico.getMatricula());

        firestore.collection("medicos")
                .document(String.valueOf(medico.getId()))
                .set(data)
                .addOnSuccessListener(a -> Log.d("FIRESTORE", "Médico guardado."));
    }

    // ============================
    //        PACIENTES
    // ============================
    public LiveData<List<Paciente>> obtenerPacientes() {
        return pacienteDao.allPacientes();
    }

    public void insertarPaciente(Paciente paciente) {
        executor.execute(() -> {
            long id = pacienteDao.insertar(paciente);
            paciente.setId((int) id);
            guardarPacienteEnFirestore(paciente);
        });
    }

    public void actualizarPaciente(Paciente paciente) {
        executor.execute(() -> {
            pacienteDao.actualizar(paciente);
            guardarPacienteEnFirestore(paciente);
        });
    }

    public void eliminarPaciente(Paciente paciente) {
        executor.execute(() -> {
            pacienteDao.eliminar(paciente);
            firestore.collection("pacientes")
                    .document(String.valueOf(paciente.getId()))
                    .delete();
        });
    }

    private void guardarPacienteEnFirestore(Paciente paciente) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", paciente.getNombre());
        data.put("edad", paciente.getEdad());
        data.put("email", paciente.getEmail());
        data.put("diagnostico", paciente.getDiagnostico());

        firestore.collection("pacientes")
                .document(String.valueOf(paciente.getId()))
                .set(data)
                .addOnSuccessListener(a -> Log.d("FIRESTORE", "Paciente guardado."));
    }

    // ============================
    //        MEDICAMENTOS
    // ============================
    public LiveData<List<Medicamento>> obtenerMedicamentos() {
        return medicamentoDao.allMedicamentos();
    }

    public void insertarMedicamento(Medicamento medicamento) {
        executor.execute(() -> {
            long id = medicamentoDao.insertar(medicamento);
            medicamento.setId((int) id);
            guardarMedicamentoEnFirestore(medicamento);
        });
    }

    public void actualizarMedicamento(Medicamento medicamento) {
        executor.execute(() -> {
            medicamentoDao.actualizar(medicamento);
            guardarMedicamentoEnFirestore(medicamento);
        });
    }

    public void eliminarMedicamento(Medicamento medicamento) {
        executor.execute(() -> {
            medicamentoDao.eliminar(medicamento);
            firestore.collection("medicamentos")
                    .document(String.valueOf(medicamento.getId()))
                    .delete();
        });
    }

    private void guardarMedicamentoEnFirestore(Medicamento medicamento) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", medicamento.getNombre());
        data.put("uso", medicamento.getUso());
        data.put("dosis", medicamento.getDosis());
        data.put("efectos", medicamento.getEfectos());
        data.put("precio", medicamento.getPrecio());
        data.put("vencimiento", medicamento.getVencimiento());

        firestore.collection("medicamentos")
                .document(String.valueOf(medicamento.getId()))
                .set(data)
                .addOnSuccessListener(a -> Log.d("FIRESTORE", "Medicamento guardado."));
    }
}
