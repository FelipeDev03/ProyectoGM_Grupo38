package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraVida implements Comprable {
    
    private int costo = 75;

    @Override
    public String getNombre() {
        return "Botiquin";
    }

    @Override
    public String getDescripcion() {
        return "Restaura 1 vida.";
    }

    @Override
    public int getCosto() {
        return costo;
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
        nave.setVidas(nave.getVidas() + 1);
        Gdx.app.log("Tienda", "Vida curada!");
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}