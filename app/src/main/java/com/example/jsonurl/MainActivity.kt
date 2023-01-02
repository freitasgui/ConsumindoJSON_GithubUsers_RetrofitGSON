package com.example.jsonurl

import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.create

private var txt : String? = null

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = Utils.getInstance().create(ApiInterface::class.java)

        val texto : TextView = findViewById(R.id.texto)

        // Se inserir sem o (Dispatchers.Main, CoroutineStart.DEFAULT) o Global Scope não consegue atualizar os dados da intent.

       GlobalScope.launch (Dispatchers.Main, CoroutineStart.DEFAULT){
           val results = user.getUsers()

           if (results.isSuccessful && results.body() != null) {
               Log.d("Tag", "${results.body()}")
               texto.setText("${results.body()!![4-1].login}" + "${results.body()!![4-1].id}") // Aqui estou exibindo os dados do indice 4, lembrando que o array começa em zero. Seguido do ID conforme a JSON https://api.github.com/users
           }
       }

    }
}