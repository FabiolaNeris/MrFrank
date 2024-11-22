package com.example.mrfrank

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mrfrank.databinding.ListItemDispositivosBinding
import com.example.mrfrank.model.Dispositivo

class DispositivoAdapter(private val dispositivos: List<Dispositivo>):
    RecyclerView.Adapter<DispositivoAdapter.DispositivoViewHolder>(){

//    class DispositivoViewHolder(itemView: ListItemDispositivosBinding) : RecyclerView.ViewHolder(itemView){
//        val nome: TextView = itemView.findViewById(R.id.textNomeDispositivo)
//        val tempoMedioUso: TextView = itemView.findViewById(R.id.textTempoMedioUso)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DispositivoViewHolder {
        val binding = ListItemDispositivosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DispositivoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DispositivoViewHolder, position: Int) {
        val dispositivo = dispositivos[position]
        holder.bind(dispositivo)
    }

    override fun getItemCount(): Int = dispositivos.size

    inner class DispositivoViewHolder(private val binding: ListItemDispositivosBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dispositivo: Dispositivo) {
            binding.textNomeDispositivo.text = dispositivo.nome
            binding.textTempoMedioUso.text = "Tempo Médio de Uso: ${dispositivo.tempoMedioUso}"
        }
    }
}