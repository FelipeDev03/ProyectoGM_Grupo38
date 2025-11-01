package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraDano implements Comprable {
    
    private int costo = 100;

    @Override
    public String getNombre() {
        return "Balas Mejoradas";
    }

    @Override
    public String getDescripcion() {
        return "Aumenta el daño de las balas.";
    }

    @Override
    public int getCosto() {
        return costo;
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
        Gdx.app.log("Tienda", "Dano aumentado!");
        costo += 50; 
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}