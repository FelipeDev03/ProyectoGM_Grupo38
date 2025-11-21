package io.github.GameSpace;

import com.badlogic.gdx.Gdx;

public class MejoraVida implements Comprable {
    
    private int costo = 150;

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
        return costo;
    }

    public void setCosto(int nuevoCosto) {
        this.costo = nuevoCosto;
    }

    @Override
    public void aplicarMejora(Nave4 nave, PantallaJuego juego) {
        nave.setVidas(nave.getVidas() + 1);
        Gdx.app.log("Tienda", "Vida recuperada! Total: " + nave.getVidas());
        costo += 50;
    }

    @Override
    public boolean esMejoraUnica() {
        return false; 
    }
}