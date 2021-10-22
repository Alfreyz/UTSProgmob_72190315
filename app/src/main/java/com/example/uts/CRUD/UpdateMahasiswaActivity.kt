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

class UpdateMahasiswaActivity : AppCompatActivity() {
    lateinit var edid : EditText
    lateinit var edkode : EditText
    lateinit var ednama : EditText
    lateinit var edhari : EditText
    lateinit var edsesi : EditText
    lateinit var edsks : EditText
    lateinit var ednim : EditText
    lateinit var edcari : EditText
    lateinit var btnSimpanMahasiswa : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_mahasiswa)
        edid = findViewById(R.id.edId)
        edkode = findViewById(R.id.edKode)
        ednama = findViewById(R.id.edNama)
        edhari = findViewById(R.id.edHari)
        edsesi = findViewById(R.id.edSesi)
        edsks = findViewById(R.id.edSks)
        ednim = findViewById(R.id.edNim_progmob)
        edcari = findViewById(R.id.edKode_cari)

        if (intent.extras != null){
            var bundle: Bundle? = intent.extras
            var strid: String = bundle?.getString("idMahasiswa").toString()
            var strkode: String = bundle?.getString("kodeMahasiswa").toString()
            var strnama: String = bundle?.getString("namaMahasiswa").toString()
            var strhari: String = bundle?.getString("hariMahasiswa").toString()
            var strsesi: String = bundle?.getString("sesiMahasiswa").toString()
            var strsks: String = bundle?.getString("sksMahasiswa").toString()
            edid.setText(strid)
            edkode.setText(strkode)
            ednama.setText(strnama)
            edhari.setText(strhari)
            edsesi.setText(strsesi)
            edsks.setText(strsks)
            edcari.setText(strkode)
        }

        btnSimpanMahasiswa = findViewById(R.id.btnSimpanMahasiswa)
        btnSimpanMahasiswa.setOnClickListener(View.OnClickListener { view ->
            var mhs = ResponseMahasiswaItem()
            mhs.id = edid.text.toString()
            mhs.kode = edkode.text.toString()
            mhs.nama = ednama.text.toString()
            mhs.hari = edhari.text.toString()
            mhs.sesi = edsesi.text.toString()
            mhs.sks = edsks.text.toString()
            mhs.nim_progmob = ednim.text.toString()

            NetworkConfig().getService()
                .updateMahasiswa(mhs)
                .enqueue(object : Callback<ResponseAdd?> {
                    override fun onFailure(call: Call<ResponseAdd?>, t:
                    Throwable) {
                        Toast.makeText(this@UpdateMahasiswaActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(
                        call: Call<ResponseAdd?>,
                        response: Response<ResponseAdd?>
                    ) {
                        Toast.makeText(
                            this@UpdateMahasiswaActivity,
                            "Berhasil Ubah Data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
        })
    }
}