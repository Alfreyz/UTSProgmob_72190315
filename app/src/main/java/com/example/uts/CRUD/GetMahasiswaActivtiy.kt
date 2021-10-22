package com.example.uts.CRUD

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.R
import com.example.uts.adapter.ResponseMahasiswaAdapater
import com.example.uts.model.ResponseMahasiswa
import com.example.uts.model.ResponseMahasiswaItem
import com.example.uts.network.NetworkConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetMahasiswaActivtiy : AppCompatActivity() {
    lateinit var rvmahasiswa : RecyclerView
    lateinit var fabaddmahasiswa : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_mahasiswa_activtiy)
        rvmahasiswa = findViewById(R.id.rvMahasiswa)
        fabaddmahasiswa = findViewById(R.id.fabaddMahasiswa)
        NetworkConfig().getService()
            .getMahasiswa("72190315")
            .enqueue(object : Callback<List<ResponseMahasiswaItem>?> {
                override fun onFailure(call: Call<List<ResponseMahasiswaItem>?>, t:
                Throwable) {
                    Toast.makeText(this@GetMahasiswaActivtiy, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<List<ResponseMahasiswaItem>?>,
                    response: Response<List<ResponseMahasiswaItem>?>
                ) {
                    rvmahasiswa.apply{
                        layoutManager = LinearLayoutManager(this@GetMahasiswaActivtiy)
                        adapter = ResponseMahasiswaAdapater(response.body() as List<ResponseMahasiswaItem>?)
                    }
                }
            })
        fabaddmahasiswa.setOnClickListener(View.OnClickListener { view ->
            var intent = Intent(this@GetMahasiswaActivtiy, AddMahasiswaActivity::class.java)
            startActivity(intent)
        })
    }
}