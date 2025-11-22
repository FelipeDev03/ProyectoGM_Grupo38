package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraDano implements Comprable {

    @Override
    public String getNombre() {
        return "Mejora de balas";
    }

    @Override
    public String getDescripcion() {
        return "Aumenta el daño de las balas en +1.";
    }

    public void setCosto(int nuevoCosto) {
    }
    
    @Override
    public int getCosto() {
        return GameManager.getInstancia().getCostoDano();
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
    	GameManager gm = GameManager.getInstancia();
    	gm.aumentarDano();              // Sube el daño en el Manager
        gm.aumentarCostoDano(200);      // Sube el precio en el Manager
        this.setCosto(gm.getCostoDano()); // Actualizamos costo local para la UI
    	Gdx.app.log("Tienda", "Daño aumentado a: " + gm.getDanoJugador());
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}