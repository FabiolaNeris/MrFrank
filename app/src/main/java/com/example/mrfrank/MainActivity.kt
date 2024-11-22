package com.example.mrfrank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrfrank.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener{
            createUser()
        }

        binding.textEsqueceuSenha.setOnClickListener{
            forgetPassword()
        }

    }

    private fun createUser(){

        val email = binding.textEmail.text.toString()
        val senha = binding.textSenha.text.toString()

        if(email.isEmpty() || senha.isEmpty()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        if(!isValidEmail(email)){
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,senha)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Cadastro realizado com sucesso",
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Erro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun forgetPassword(){
        val email = binding.textEmail.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this, "Digite seu email para recuperar a senha",
                Toast.LENGTH_SHORT).show()
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Email de recuperação enviado",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao enviar email de recuperação: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isValidEmail(email: String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
