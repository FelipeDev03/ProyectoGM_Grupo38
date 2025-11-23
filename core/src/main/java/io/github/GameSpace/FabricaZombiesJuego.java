package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;

public class FabricaZombiesJuego implements ZombieFactory {
    private int ronda;
    private float velocidadBase;

    public FabricaZombiesJuego(int ronda) {
        this.ronda = ronda;
        this.velocidadBase = 1.5f; 
    }

    private int getSaludBase() {
        return 2 + this.ronda;
    }

    @Override
    public ZombieNormal crearZombieNormal(int x, int y) {
        Texture tex = Recursos.getInstancia().getTexturaZombie();
        ZombieNormal z = new ZombieNormal(x, y, velocidadBase, tex);
        z.health = getSaludBase();
        return z;
    }

    @Override
    public ZombieRapido crearZombieRapido(int x, int y) {
        Texture tex = Recursos.getInstancia().getTexturaZombie();
        ZombieRapido z = new ZombieRapido(x, y, velocidadBase, tex);
        z.health = (int)(getSaludBase() * 0.5f);
        return z;
    }

    @Override
    public ZombieTanque crearZombieTanque(int x, int y) {
        Texture tex = Recursos.getInstancia().getTexturaZombie();
        ZombieTanque z = new ZombieTanque(x, y, velocidadBase, tex);
        z.health = getSaludBase() * 3;
        return z;
    }
}