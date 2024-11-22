package com.example.mrfrank

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

    private val addDeviceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loadDispositivos()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySalaDeEstarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        binding.recyclerViewDispositivos.layoutManager = LinearLayoutManager(this)

        loadDispositivos()

        binding.btnVoltar.setOnClickListener{
            intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnAdicionar.setOnClickListener{ view ->
            showPopupMenu(view)
        }

    }

    private fun showPopupMenu(view: View){
        val popupMenu = PopupMenu(this, view)

        popupMenu.menu.add(Menu.NONE, 1, 1, "Adicionar Dispositivo")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Editar Dispositivo")
        popupMenu.menu.add(Menu.NONE, 3, 3, "Remover Dispositivo")


        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId){
                1 ->{
                    addNewDevice()
                    return@setOnMenuItemClickListener true
                }
                2 ->{
                    editDevice()
                    return@setOnMenuItemClickListener true
                }
                3 -> {
                    removeDevice()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
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
    private fun addNewDevice() {
        val intent = Intent(this, AdicionarDispositivo::class.java)
        addDeviceLauncher.launch(intent)
    }

    private fun editDevice() {
        val dispositivos = mutableListOf<String>()
        val dispositivosId = mutableListOf<String>()


        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val nome = document.getString("nome")
                    dispositivos.add(nome ?: "Desconhecido")
                    dispositivosId.add(document.id)
                }


                val builder = AlertDialog.Builder(this)
                builder.setTitle("Escolha um dispositivo para editar")
                builder.setItems(dispositivos.toTypedArray()) { _, which ->
                    val dispositivoId = dispositivosId[which]

                    val intent = Intent(this, EditarDispositivo::class.java)
                    intent.putExtra("dispositivoId", dispositivoId)
                    startActivity(intent)
                }
                builder.show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar dispositivos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removeDevice() {
        val dispositivos = mutableListOf<String>()
        val dispositivosId = mutableListOf<String>()


        db.collection("comodos")
            .document("sala_de_estar")
            .collection("dispositivos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val nome = document.getString("nome")
                    dispositivos.add(nome ?: "Desconhecido")
                    dispositivosId.add(document.id)  // Armazenando o ID do dispositivo
                }


                val builder = AlertDialog.Builder(this)
                builder.setTitle("Escolha um dispositivo para remover")
                builder.setItems(dispositivos.toTypedArray()) { _, which ->
                    val dispositivoId = dispositivosId[which]

                    AlertDialog.Builder(this)
                        .setMessage("Tem certeza que deseja remover este dispositivo?")
                        .setPositiveButton("Sim") { _, _ ->
                            db.collection("comodos")
                                .document("sala_de_estar")
                                .collection("dispositivos")
                                .document(dispositivoId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Dispositivo removido com sucesso!", Toast.LENGTH_SHORT).show()
                                    loadDispositivos()  // Atualizar a lista após a remoção
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(this, "Erro ao remover dispositivo: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("Não", null)
                        .show()
                }
                builder.show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar dispositivos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


}