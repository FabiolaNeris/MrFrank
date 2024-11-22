package com.example.mrfrank

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrfrank.databinding.ActivitySalaDeEstarBinding
import com.example.mrfrank.model.Dispositivo
import com.google.firebase.firestore.FirebaseFirestore

class SalaDeEstar : AppCompatActivity() {

    private lateinit var binding: ActivitySalaDeEstarBinding
    private lateinit var db:FirebaseFirestore
    private lateinit var dispositivoAdapter: DispositivoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySalaDeEstarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.recyclerViewDispositivos.layoutManager = LinearLayoutManager(this)

        loadDispositivos()

    }

    private fun loadDispositivos(){
        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .get()
            .addOnSuccessListener { result ->
                val dispositivos = mutableListOf<Dispositivo>()
                for (document in result){
                    val nome = document.getString("nome")
                    val tempoMedioUso = document.getString("tempo_medio_uso")

                    dispositivos.add(Dispositivo(nome, tempoMedioUso))
                }
                dispositivoAdapter = DispositivoAdapter(dispositivos)
                binding.recyclerViewDispositivos.adapter = dispositivoAdapter
            }
            .addOnFailureListener{exception ->
                Toast.makeText(this, "Erro ao carregar dispositivos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }


    }
}