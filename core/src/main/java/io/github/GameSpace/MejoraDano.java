package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraDano implements Comprable {
    
    private int costo = 250;

    @Override
    public String getNombre() {
        return "Mejora de balas";
    }

    @Override
    public String getDescripcion() {
        return "Aumenta el daño de las balas en +1.";
    }

    public void setCosto(int nuevoCosto) {
        this.costo = nuevoCosto;
    }
    
    @Override
    public int getCosto() {
        return costo;
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
    	nave.aumentarDano(1);
    	Gdx.app.log("Tienda", "Daño aumentado a: " + nave.getDanoDisparo());
        costo += 200; 
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}