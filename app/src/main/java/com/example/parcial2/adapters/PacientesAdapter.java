package com.example.parcial2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.model.Paciente;

import java.util.List;

public class PacientesAdapter extends RecyclerView.Adapter<PacientesAdapter.PacienteViewHolder> {

    private List<Paciente> pacientes;

    public PacientesAdapter(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente, parent, false);
        return new PacienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        Paciente paciente = pacientes.get(position);
        holder.tvNombre.setText(paciente.getNombre());
        holder.tvEdad.setText(String.valueOf(paciente.getEdad()));
        holder.tvEmail.setText(paciente.getEmail());
        holder.tvDiagnostico.setText(paciente.getDiagnostico());
    }

    @Override
    public int getItemCount() {
        return pacientes.size();
    }

    // Método para actualizar la lista desde LiveData
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
        notifyDataSetChanged();
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEdad, tvEmail, tvDiagnostico;

        public PacienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEdad = itemView.findViewById(R.id.tvEdad);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDiagnostico = itemView.findViewById(R.id.tvDiagnostico);
        }
    }
}
