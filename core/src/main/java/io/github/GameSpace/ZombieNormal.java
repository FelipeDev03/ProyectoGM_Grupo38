package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;

// GM1.4: Clase hija 1
public class ZombieNormal extends Zombie {

    public ZombieNormal(int x, int y, float speed, Texture tx) {
        // Llama al constructor padre: x, y, size, speed, health, texture
        // (GM1.2) Incrementamos la salud base de los zombis
        super(x, y, 45, speed, 10, tx); // Salud base de 10
    }

    @Override
    public void onDeath() {
        // Lógica específica al morir (por ahora solo un log)
        // Gdx.app.log("ZombieNormal", "Muerto!");
    }
}