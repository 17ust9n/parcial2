package com.example.parcial2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.R;
import com.example.parcial2.model.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class MedicamentosAdapter extends RecyclerView.Adapter<MedicamentosAdapter.MedicamentoViewHolder> {

    private List<Medicamento> lista = new ArrayList<>();
    private OnMedicamentoClickListener listener;

    // Interface de callback para manejar clics
    public interface OnMedicamentoClickListener {
        void onClick(Medicamento medicamento);
    }

    public void setOnMedicamentoClickListener(OnMedicamentoClickListener listener) {
        this.listener = listener;
    }

    public void actualizarLista(List<Medicamento> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento m = lista.get(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvUso.setText("Uso: " + m.getUso());
        holder.tvLaboratorio.setText("Laboratorio: " + m.getLaboratorio());
        holder.tvDosis.setText("Dosis: " + m.getDosis());
        holder.tvEfectos.setText("Efectos secundarios: " + m.getEfectos());
        holder.tvPrecio.setText("Precio: " + m.getPrecio());
        holder.tvVencimiento.setText("Vencimiento: " + m.getVencimiento());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(m);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class MedicamentoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvUso, tvLaboratorio, tvDosis,
                tvEfectos, tvPrecio, tvVencimiento;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombreMedicamentoItem);
            tvUso = itemView.findViewById(R.id.tvUsoMedicamentoItem);
            tvLaboratorio = itemView.findViewById(R.id.tvLaboratorioMedicamentoItem);
            tvDosis = itemView.findViewById(R.id.tvDosisMedicamentoItem);
            tvEfectos = itemView.findViewById(R.id.tvEfectosMedicamentoItem);
            tvPrecio = itemView.findViewById(R.id.tvPrecioMedicamentoItem);
            tvVencimiento = itemView.findViewById(R.id.tvVencimientoMedicamentoItem);
        }
    }
}
