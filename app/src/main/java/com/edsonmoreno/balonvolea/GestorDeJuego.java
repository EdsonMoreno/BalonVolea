package com.edsonmoreno.balonvolea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;



public class GestorDeJuego extends Activity {
    private Juego game;
    private int level;
    private int fps = 30;
    private Handler aps;
    private int boleo;
    private Runnable hilo_pelota = new Runnable(){

        @Override
        public void run() {
            if(game.movimientoBola()){
                fin();
            }else {
                //limpiamos la pantalla para borrar lo que se estaba dibujando
                /**
                 * Generalmente, invalidate () significa 'redibujar en la pantalla'
                 * y resulta en una llamada del método onDraw () de la vista. Entonces,
                 * si algo cambia y debe reflejarse en la pantalla, debe llamar a invalidate ()
                 */
                game.invalidate();
                //ahora decidimos cada cuanto ejecutamos esto
                aps.postDelayed(hilo_pelota, 1000/fps);
            }
        }
    };

    @Override
     protected void onCreate(Bundle saveInstanceState){
         super.onCreate(saveInstanceState);
         boleo = 0;
         Bundle recibido = super.getIntent().getExtras();
         level = recibido.getInt("level");
         game = new Juego(this.getApplicationContext(), level);
        this.setContentView(game);
        //iniciamos el hilo
        aps= new Handler();
        aps.postDelayed(hilo_pelota, 500);
     }

     public boolean onTouchEvent(MotionEvent motionEvent){
        //Donde pulso el usuario
         int voordenada_x = (int)motionEvent.getX();
         int voordenada_y = (int)motionEvent.getY();
         if(game.toque(voordenada_x,voordenada_y))  boleo++;
         return false;
     }

     public void fin(){
        //verificamos que el hilo se detenga
         aps.removeCallbacks(hilo_pelota);
        //finalizamos la actividad actual
         Intent i = new Intent();
         i.putExtra("puntos",this.boleo);
         //decir que todo ok y envia el intent de retorno
         this.setResult(RESULT_OK,i);
        this.finish();
     }
}
