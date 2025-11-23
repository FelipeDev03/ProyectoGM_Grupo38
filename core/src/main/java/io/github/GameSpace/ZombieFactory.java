package io.github.GameSpace;

public interface ZombieFactory {
    ZombieNormal crearZombieNormal(int x, int y);
    ZombieRapido crearZombieRapido(int x, int y);
    ZombieTanque crearZombieTanque(int x, int y);
}