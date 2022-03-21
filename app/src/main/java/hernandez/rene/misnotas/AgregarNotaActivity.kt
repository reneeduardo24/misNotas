package hernandez.rene.misnotas

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class AgregarNotaActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        var btn_guardar: Button = findViewById(R.id.btn_guardar)

        btn_guardar.setOnClickListener{
            guardar_nota();
        }
    }
    fun guardar_nota(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235
            )
        }else{
            guardar()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)  {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            235 ->{
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))  {
                    guardar()
                } else{
                    Toast.makeText(this, "Error: permisos denegados", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public fun guardar() {

        var titulo: EditText = findViewById(R.id.et_titulo)
        var tituloStr: String = titulo.text.toString()

        var cuerpo: EditText = findViewById(R.id.et_contenido)
        var cuerpoStr: String = cuerpo.text.toString()


        if (tituloStr == "" || cuerpoStr == ""){

            Toast.makeText(this, "Error: Campos vacios", Toast.LENGTH_SHORT).show()
        } else{
            try{
                val archivo  = File(ubicacion(), tituloStr + ".txt")
                val fos = FileOutputStream(archivo)
                fos.write(cuerpoStr.toByteArray())
                fos.close()
                Toast.makeText(
                    this,
                    "se guardó el archivo en la carpeta pública",
                    Toast.LENGTH_SHORT
                ).show()

            }catch (e: Exception){
                Toast.makeText(this, "Error: No se guardó el archivo", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun ubicacion(): String{
        val carpeta = File(getExternalFilesDir(null), "notas")
        if (!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta.absolutePath
    }





}


