package com.example.parcial2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.parcial2.R;
import com.example.parcial2.model.Medico;
import java.util.List;

public class MedicosAdapter extends RecyclerView.Adapter<MedicosAdapter.MedicoViewHolder> {

    private List<Medico> medicos;

    public MedicosAdapter(List<Medico> medicos) {
        this.medicos = medicos;
    }

    @NonNull
    @Override
    public MedicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medico, parent, false);
        return new MedicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicoViewHolder holder, int position) {
        Medico medico = medicos.get(position);
        holder.tvNombre.setText(medico.getNombre());
        holder.tvEspecialidad.setText(medico.getEspecialidad());
        holder.tvEmail.setText(medico.getEmail());
        holder.tvTelefono.setText(medico.getTelefono());
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
        notifyDataSetChanged();
    }

    static class MedicoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEspecialidad, tvEmail, tvTelefono;

        public MedicoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEspecialidad = itemView.findViewById(R.id.tvEspecialidad);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }
    }
}
