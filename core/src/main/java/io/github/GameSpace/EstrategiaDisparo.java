package io.github.GameSpace;

public interface EstrategiaDisparo {
	/**
     * @param x Posición X de origen (punta de la nave)
     * @param y Posición Y de origen
     * @param anguloRad Ángulo de rotación actual en radianes
     * @param juego Referencia al juego para agregar las balas a la lista
     */
   void disparar(float x, float y, float anguloRad, PantallaJuego juego);

}
