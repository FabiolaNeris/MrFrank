package com.example.mrfrank

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrfrank.databinding.ActivityAdicionarDispositivoBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdicionarDispositivo : AppCompatActivity() {

    private val binding by lazy{
        ActivityAdicionarDispositivoBinding.inflate(layoutInflater)
    }

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.btnAddDevice.setOnClickListener{
            val nomeDispositivo = binding.editNomeDevice.text.toString()
            val descricao = binding.editDescricao.text.toString()
            val marca = binding.editMarca.text.toString()
            val voltagem = binding.editVoltagem.text.toString()
            val tempoUso = binding.editMediaUso.text.toString()


            if(nomeDispositivo.isNotEmpty() && descricao.isNotEmpty() && marca.isNotEmpty() && voltagem.isNotEmpty() && tempoUso.isNotEmpty()){
                salvarDispositivo(nomeDispositivo, descricao, marca, voltagem, tempoUso)
            }else{
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelar.setOnClickListener{
            finish()
        }

    }

    private fun salvarDispositivo(nome: String, descricao: String, marca: String, voltagem:String, tempoUso:String){
        val dispositivo = hashMapOf(
            "nome" to nome,
            "descricao" to descricao,
            "marca" to marca,
            "voltagem" to voltagem,
            "tempo_medio_uso" to tempoUso
        )

        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .add(dispositivo)
            .addOnSuccessListener { Toast.makeText(this, "Novo Dispositivo adicionado com sucesso", Toast.LENGTH_SHORT).show()

            setResult(Activity.RESULT_OK)
            finish()
            }
            .addOnFailureListener{exception ->
                Toast.makeText(this, "Erro ao adicionar dispositivo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}