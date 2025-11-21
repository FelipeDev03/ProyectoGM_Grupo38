package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;

public class ZombieNormal extends Zombie {

    public ZombieNormal(int x, int y, float speed, Texture tx) {
        super(x, y, 45, speed, 10, tx); // Salud base de 10
    }

    @Override
    public void onDeath() {
 
    }
}