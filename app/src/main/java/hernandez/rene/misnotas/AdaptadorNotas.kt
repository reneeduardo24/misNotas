package hernandez.rene.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.lang.Exception

class AdaptadorNotas: BaseAdapter {

    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notas: ArrayList<Nota>){
        this.context = context
        this.notas = notas
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout, null)
        var nota = notas[p0]


        var tv_titulo_det: TextView = vista.findViewById(R.id.tv_contenido_det)

        var tv_contenido_det: TextView = vista.findViewById(R.id.tv_contenido_det)

        var btn_borrar: Button = vista.findViewById(R.id.btn_guardar)

        tv_titulo_det.setText(nota.titulo)
        tv_contenido_det.setText(nota.contenido)

        btn_borrar.setOnClickListener{
            eliminar(nota.titulo)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista

    }

    private fun eliminar(titulo: String){
        if (titulo == ""){
            Toast.makeText(context, "Error: título vacío", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo = File(ubicacion(), titulo+".txt")
                archivo.delete()

                Toast.makeText(context, "Se eliminó el archivo", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Toast.makeText(context, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun ubicacion(): String {
        val album = File(context?.getExternalFilesDir(null), "notas")
        if (!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }


    override fun getItem(p0: Int): Any {
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return notas.size
    }


}