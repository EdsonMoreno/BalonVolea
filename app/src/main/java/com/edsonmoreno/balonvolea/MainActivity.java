package com.edsonmoreno.balonvolea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Ayuda(View view){
        Intent i = new Intent(this, AyudaActivity.class);
        //para esperar un resultado de una actividad
        super.startActivityForResult(i,1);
    }

    public void dificultad(View view){
        String dific = (String) ((Button)view).getText();
        int dificultad = 1;
        if (dific.equals( this.getString(R.string.facil)))
            dificultad = 1;
        else if(dific.equals(this.getString(R.string.medio)))
            dificultad = 2;
        else if( dific.equals(this.getString(R.string.dificil)))
            dificultad = 3;

        Intent i = new Intent(this, GestorDeJuego.class);
        i.putExtra("level",dificultad);
        //para esperar un resultado de una actividad
        super.startActivityForResult(i,1);
    }

    @Override
    protected  void onActivityResult(int peticion, int codigo, Intent intent) {
        if(peticion != 1 || codigo != RESULT_OK){ return; }
        int puntuacion = intent.getIntExtra("puntos",0);
        if(puntuacion > record) {
            record = puntuacion;
            TextView t = (TextView) this.findViewById(R.id.record);
            t.setText("Mejor Puntuacion: "+record);;
            this.gyardarRecord();
        }
        else{
            String puntuacion_= "Puntuacion "+puntuacion;
            Toast t = Toast.makeText(this, puntuacion_, Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
        }

        super.onActivityResult(peticion, codigo, intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        reescatarRecord();

    }

    protected void reescatarRecord(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.record = sp.getInt("record",0);
        TextView textView = (TextView) findViewById(R.id.record);
        textView.setText("Mejor Puntuacion: "+record);
    }

    private void gyardarRecord(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt("record",this.record);
        spe.apply();
    }
    private int record;

}