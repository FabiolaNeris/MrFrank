package com.example.mrfrank

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrfrank.databinding.ActivityAdicionarDispositivoBinding
import com.example.mrfrank.databinding.ActivityEditarDispositivoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.handleCoroutineException

class EditarDispositivo : AppCompatActivity() {

    private lateinit var binding: ActivityEditarDispositivoBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var dispositivoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditarDispositivoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        dispositivoId = intent.getStringExtra("dispositivoId")?:""

        if (dispositivoId.isNotEmpty()) {
            loadDeviceDetails()
        } else {
            Toast.makeText(this, "ID do dispositivo inválido", Toast.LENGTH_SHORT).show()
            finish() // Fecha a activity se o ID for inválido
        }


        binding.btnSalvar.setOnClickListener {
            saveDeviceChanges()
        }

        binding.btnCancelar.setOnClickListener{
            finish()
        }
    }

    private fun loadDeviceDetails() {
        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .document(dispositivoId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val descricao = document.getString("descricao")
                    val marca = document.getString("marca")
                    val voltagem = document.getString("voltagem")

                    // Exibir os dados nos campos de texto
                    binding.edtDescricao.setText(descricao)
                    binding.edtVoltagem.setText(voltagem)
                    binding.edtMarca.setText(marca)
                } else {
                    Toast.makeText(this, "Dispositivo não encontrado", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar dispositivo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveDeviceChanges(){
        val descricao = binding.edtDescricao.text.toString()
        val marca = binding.edtMarca.text.toString()
        val voltagem = binding.edtVoltagem.text.toString()

        if(descricao.isBlank() || marca.isBlank() || voltagem.isBlank()){
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val dispositivoDados = hashMapOf(
            "descricao" to descricao,
            "marca" to marca,
            "voltagem" to voltagem
        )

        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .document(dispositivoId)
            .set(dispositivoDados, SetOptions.merge())
            .addOnSuccessListener { Toast.makeText(this, "Dispositivo atualizado com sucesso", Toast.LENGTH_SHORT).show()
                finish() }
            .addOnFailureListener{ exception ->
                Toast.makeText(this, "Erro ao atualizar dispositivo: ${exception.message}", Toast.LENGTH_SHORT).show()
            }


    }
}
