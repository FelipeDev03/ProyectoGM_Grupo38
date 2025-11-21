package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;

public class ZombieTanque extends Zombie {

    public ZombieTanque(int x, int y, float speed, Texture tx) {
 
        super(x, y, 60, speed * 0.6f, 10, tx); 
        
        // Color rojo
        spr.setColor(1.0f, 0.6f, 0.6f, 1.0f); 
    }

    @Override
    public void onDeath() {
        // Este método es requerido por la clase abstracta 'Zombie'

    }
}
