package com.example.uts.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.CRUD.GetMahasiswaActivtiy
import com.example.uts.CRUD.UpdateMahasiswaActivity
import com.example.uts.R
import com.example.uts.model.ResponseAdd
import com.example.uts.model.ResponseMahasiswaItem
import com.example.uts.network.NetworkConfig
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResponseMahasiswaAdapater(val mahasiswa: List<ResponseMahasiswaItem>?): RecyclerView.Adapter<ResponseMahasiswaAdapater.ResponseMahasiswaHolder>() {
    lateinit var mContext : Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResponseMahasiswaAdapater.ResponseMahasiswaHolder {
        return ResponseMahasiswaHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_mahasiswa, parent, false))
    }
    override fun onBindViewHolder(holder: ResponseMahasiswaAdapater.ResponseMahasiswaHolder, position: Int) {
        holder.bindMahasiswa(mahasiswa?.get(position))
        var popupMenu = PopupMenu(holder.itemView.context, holder.itemView)
        popupMenu.menu.add(Menu.NONE,0,0,"Edit")
        popupMenu.menu.add(Menu.NONE,1,1,"Delete")
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId
            mContext = holder.itemView.context
            if (id==0){
                var bundle = Bundle()
                var idTmp = mahasiswa?.get(position)?.id.toString()
                var kodeTmp = mahasiswa?.get(position)?.kode.toString()
                var namaTmp = mahasiswa?.get(position)?.nama.toString()
                var hariTmp = mahasiswa?.get(position)?.hari.toString()
                var sesiTmp = mahasiswa?.get(position)?.sesi.toString()
                var sksTmp = mahasiswa?.get(position)?.sks.toString()
                bundle.putString("idMahasiswa",idTmp)
                bundle.putString("kodeMahasiswa",kodeTmp)
                bundle.putString("namaMahasiswa",namaTmp)
                bundle.putString("hariMahasiswa",hariTmp)
                bundle.putString("sesiMahasiswa",sesiTmp)
                bundle.putString("sksMahasiswa",sksTmp)
                var intent = Intent(mContext, UpdateMahasiswaActivity::class.java)
                intent.putExtras(bundle)
                mContext.startActivity(intent)
            }
            else if(id==1){
                var kodeTmp = mahasiswa?.get(position)?.kode.toString()
                NetworkConfig().getService()
                    .deleteMahasiswa(kodeTmp)
                    .enqueue(object : Callback <ResponseAdd> {
                        override fun onFailure(call: Call<ResponseAdd>, t:
                        Throwable) {
                            Toast.makeText(holder.itemView.context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<ResponseAdd>,
                            response: Response<ResponseAdd>
                        ) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Berhasil Hapus Data",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            (mContext as GetMahasiswaActivtiy).finish()
                            var intent = Intent(mContext, GetMahasiswaActivtiy::class.java)
                            mContext.startActivity(intent)
                        }
                    })
            }
            false
        }
        holder.itemView.setOnClickListener((View.OnClickListener { view ->
            popupMenu.show()
        }))
    }

    override fun getItemCount(): Int {
        return mahasiswa?.size?:0
    }

    class ResponseMahasiswaHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var txtkode : TextView
        lateinit var txtnama : TextView
        lateinit var txthari : TextView
        lateinit var txtsesi : TextView
        lateinit var txtsks : TextView

        fun bindMahasiswa(Mahasiswa: ResponseMahasiswaItem?) {
            itemView.apply {
                txtkode= findViewById(R.id.kode)
                txtnama = findViewById(R.id.nama)
                txthari = findViewById(R.id.hari)
                txtsesi = findViewById(R.id.sesi)
                txtsks = findViewById(R.id.sks)
                txtkode.text = Mahasiswa?.kode
                txtnama.text = Mahasiswa?.nama
                txthari.text = Mahasiswa?.hari
                txtsesi.text = Mahasiswa?.sesi
                txtsks.text = Mahasiswa?.sks
            }
        }
    }
}
