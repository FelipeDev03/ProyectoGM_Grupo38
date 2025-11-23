package io.github.GameSpace;

import com.badlogic.gdx.math.MathUtils;


public class DisparoDoble implements EstrategiaDisparo {
    @Override
    public void disparar(float x, float y, float anguloRad, PantallaJuego juego) {
        float velBala = 10.0f;
        int dano = GameManager.getInstancia().getDanoJugador();
        // Calculamos un desplazamiento para que las balas salgan de los lados
        float offset = 15f;        
        // Bala 1 (Derecha)
        float x1 = x + MathUtils.cos(anguloRad + MathUtils.PI / 2) * offset;
        float y1 = y + MathUtils.sin(anguloRad + MathUtils.PI / 2) * offset;      
        // Bala 2 (Izquierda)
        float x2 = x + MathUtils.cos(anguloRad - MathUtils.PI / 2) * offset;
        float y2 = y + MathUtils.sin(anguloRad - MathUtils.PI / 2) * offset;
        // Velocidades 
        float velX = MathUtils.cos(anguloRad) * velBala;
        float velY = MathUtils.sin(anguloRad) * velBala;
        // Crear y agregar balas
        juego.agregarBala(new Bullet(x1, y1, velX, velY, Recursos.getInstancia().getTexturaBala(), dano));
        juego.agregarBala(new Bullet(x2, y2, velX, velY, Recursos.getInstancia().getTexturaBala(), dano));
        Recursos.getInstancia().getSonidoDisparo().play(0.8f); 
    }
}