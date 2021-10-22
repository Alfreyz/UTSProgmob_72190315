package com.example.uts.CRUD

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uts.R
import com.example.uts.model.ResponseAdd
import com.example.uts.model.ResponseMahasiswaItem
import com.example.uts.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMahasiswaActivity : AppCompatActivity() {
    lateinit var edkode : EditText
    lateinit var ednama : EditText
    lateinit var edhari : EditText
    lateinit var edsesi : EditText
    lateinit var edsks : EditText
    lateinit var ednim : EditText
    lateinit var btnSimpanMahasiswa : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mahasiswa)
        edkode = findViewById(R.id.edKode)
        ednama = findViewById(R.id.edNama)
        edhari = findViewById(R.id.edHari)
        edsesi = findViewById(R.id.edSesi)
        edsks = findViewById(R.id.edSks)
        ednim = findViewById(R.id.edNim_progmob)
        btnSimpanMahasiswa = findViewById(R.id.btnSimpanMahasiswa)

        btnSimpanMahasiswa.setOnClickListener(View.OnClickListener { view ->
            var mhs = ResponseMahasiswaItem()
            mhs.kode = edkode.text.toString()
            mhs.nama = ednama.text.toString()
            mhs.hari = edhari.text.toString()
            mhs.sesi = edsesi.text.toString()
            mhs.sks = edsks.text.toString()
            mhs.nim_progmob = ednim.text.toString()
            mhs.id = null


            NetworkConfig().getService()
                .addMahasiswa(mhs)
                .enqueue(object : Callback<ResponseAdd?> {
                    override fun onFailure(call: Call<ResponseAdd?>, t:
                    Throwable) {
                        Toast.makeText(this@AddMahasiswaActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseAdd?>,
                        response: Response<ResponseAdd?>
                    ) {
                        Toast.makeText(
                            this@AddMahasiswaActivity,
                            "Berhasil Tambah Data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
        })
    }
}