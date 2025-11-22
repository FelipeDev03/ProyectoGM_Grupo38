package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraVida implements Comprable {

    @Override
    public String getNombre() {
        return "Botiquin";
    }

    @Override
    public String getDescripcion() {
        return "Otorga +1 vida.";
    }

    @Override
    public int getCosto() {
        return GameManager.getInstancia().getCostoVida();
    }

    public void setCosto(int nuevoCosto) {
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
    	GameManager gm = GameManager.getInstancia();
    	
    	gm.setVidasJugador(gm.getVidasJugador() + 1);
    	gm.aumentarCostoVida(50);
        Gdx.app.log("Tienda", "Vida recuperada! Total: " + gm.getVidasJugador());
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}