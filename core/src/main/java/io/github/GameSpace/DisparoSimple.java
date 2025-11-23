package io.github.GameSpace;
import com.badlogic.gdx.math.MathUtils;

public class DisparoSimple implements EstrategiaDisparo {
	@Override
	public void disparar(float x, float y, float anguloRad, PantallaJuego juego) {
		// 1. Velocidad de la bala
	    float velBala = 10.0f;
	    float velX = MathUtils.cos(anguloRad) * velBala;
	    float velY = MathUtils.sin(anguloRad) * velBala;
	    // 2. Obtener daño
	    int dano = GameManager.getInstancia().getDanoJugador();
	    // 3. Crear la bala
	    Bullet bala = new Bullet(
	    		x, y, 
	            velX, velY, 
	            Recursos.getInstancia().getTexturaBala(), 
	            dano
	        );
	    // 4. Agregar al juego
	    juego.agregarBala(bala);
	    Recursos.getInstancia().getSonidoDisparo().play();
	  }
}

