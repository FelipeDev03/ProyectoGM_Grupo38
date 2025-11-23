package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class ZombieTanque extends Zombie {
	private float tiempoRegeneracion = 0;

	public ZombieTanque(int x, int y, float speed, Texture tx) {
        super(x, y, 60, speed * 0.6f, 30, tx); // Le aumente la vida base a 30 para probar
        spr.setColor(1.0f, 0.6f, 0.6f, 1.0f); 
    }
	@Override
    protected void comportamientoEspecial() {
        // Caracteristica unica del tanque: regenerar 1 de vida cada segundo 
        tiempoRegeneracion += Gdx.graphics.getDeltaTime();
        if (tiempoRegeneracion >= 1.0f) { // Cada 1 segundo
            if (this.health < 30) { // Si está herido
                this.health++;
                //(parpadeo verde leve)
                spr.setColor(0f, 1f, 0f, 1f); 
            }
            tiempoRegeneracion = 0;
        }
    }
    @Override
    public void onDeath() {
        // Este método es requerido por la clase abstracta 'Zombie'
    }
}
