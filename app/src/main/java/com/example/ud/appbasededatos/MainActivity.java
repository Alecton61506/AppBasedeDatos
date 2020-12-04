package com.example.ud.appbasededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrearoIniciarDB();
        BorraTodo();
        Log.e("BD NumReg", String.valueOf(getNumRegistros()));
        InsertarRegistros();
        Log.e("BD NumReg", String.valueOf(getNumRegistros()));
        ConsultarRegistros();
        ActualizarRegistro();
        ConsultarRegistros();
        BorrarRegistro();
        ConsultarRegistros();
        Log.e("BD NumReg", String.valueOf(getNumRegistros()));
    }

    private void CrearoIniciarDB(){
        try{
            mydatabase =  this.openOrCreateDatabase("MYBB",MODE_PRIVATE, null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS PERSONA (ID INT, NOMBRE VARCHAR, EDAD INT, GENERO INT)");
            Log.e("BD crear", "Se creo la database y la tabla OK");

        }catch (Exception Ex){
            Log.e("Error", Ex.getMessage());
        }
    }

    private int getNumRegistros(){
        Cursor Consulta01 = mydatabase.rawQuery("select count(*) from PERSONA", null);
        Consulta01.moveToFirst();
        int NumRegistros = Consulta01.getInt(0);
        Consulta01.close();
        return NumRegistros;
    }
    private void InsertarRegistros(){
        mydatabase.execSQL("insert into  PERSONA (ID, NOMBRE, EDAD, GENERO) values (1,'Cristhian',30,0)");
        mydatabase.execSQL("insert into  PERSONA (ID, NOMBRE, EDAD, GENERO) values (2,'Alejandro',30,0)");
        mydatabase.execSQL("insert into  PERSONA (ID, NOMBRE, EDAD, GENERO) values (3,'Pauline',29,1)");
        Log.e("BD InsReg", "OK");
    }
    private void BorraTodo(){
        mydatabase.execSQL("delete from PERSONA");
        Log.e("BD DelReg", "OK");
    }
    private void ConsultarRegistros(){

        Cursor Consulta01 = mydatabase.rawQuery("select * from PERSONA", null);
        int NombreIndex = Consulta01.getColumnIndex("NOMBRE");
        int EdadIndex = Consulta01.getColumnIndex("EDAD");
        int GeneroIndex = Consulta01.getColumnIndex("GENERO");
        Log.e("BD INDICES", "Nombre: "+ NombreIndex+ ", Edad: "+EdadIndex+", Genero: "+GeneroIndex);

        while(Consulta01.moveToNext()){
            int NumRegistros = Consulta01.getInt(0);
            String Nombre= Consulta01.getString(NombreIndex);
            int Edad= Consulta01.getInt(EdadIndex);
            int Genero = Consulta01.getInt(GeneroIndex);
            Log.e("DB Registro No", String.valueOf(NumRegistros));
            Log.e("DB Datos","Nombre: "+Nombre+", Edad: "+String.valueOf(Edad)+", Genero: "+String.valueOf(Genero));
        }
        Consulta01.close();

    }
    private void ActualizarRegistro(){
        mydatabase.execSQL("update Persona set edad = 45, nombre= 'Pepito Perez' where ID = 1");
        Log.e("BD ActReg", "OK");
    }
    private void BorrarRegistro(){
        mydatabase.execSQL("delete from Persona where ID = 2");
        Log.e("BD DelReg", "OK");
    }

}