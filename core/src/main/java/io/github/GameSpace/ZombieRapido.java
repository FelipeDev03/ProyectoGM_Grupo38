package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;

// GM1.4: Clase hija 2 (Zombie Especial)
public class ZombieRapido extends Zombie {

    public ZombieRapido(int x, int y, float speed, Texture tx) {
        // Llama al constructor padre: x, y, size, speed, health, texture
        // (GM1.2) Tiene el doble de velocidad y la mitad de vida
        super(x, y, 35, speed * 2.0f, 5, tx); 
        spr.setColor(0.5f, 1.0f, 0.5f, 1.0f); // Tinte verde para diferenciarlo
    }

    @Override
    public void onDeath() {
        // Gdx.app.log("ZombieRapido", "Muerto!");
    }
}