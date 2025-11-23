package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle; // <-- IMPORTANTE: Añadir esto

public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private Sprite spr;
    private Sound sonidoHerido;
    private EstrategiaDisparo estrategia;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private int danoDisparo = 1;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque) {
    	sonidoHerido = soundChoque;
    	spr = new Sprite(tx);
    	spr.setSize(64, 64); // tamaño visible
    	spr.setOriginCenter();
    	spr.setPosition(x, y);
    	this.estrategia = new DisparoSimple();
    }
    
    
    public void draw(SpriteBatch batch, PantallaJuego juego){

        // Declara la variable de ángulo
        float angleRadians; 
        
        // Logica apuntado
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        float playerX = spr.getX() + spr.getOriginX();
        float playerY = spr.getY() + spr.getOriginY();

        angleRadians = MathUtils.atan2(mouseY - playerY, mouseX - playerX);
        
        float angleDegrees = angleRadians * MathUtils.radiansToDegrees;
        spr.setRotation(angleDegrees - 90);

        // LÓGICA DE DIBUJADO (CONDICIONAL)
        if (!herido) {
            spr.draw(batch);
        } else {
        	float xOriginal = spr.getX();
        	spr.setColor(1, 0, 0, 1);
            spr.setX(xOriginal+MathUtils.random(-2,2));
            spr.draw(batch);
            spr.setColor(1, 1, 1, 1);
            spr.setX(xOriginal); 
            tiempoHerido--;
            if (tiempoHerido<=0) herido = false;
         }
        
        // LÓGICA DE DISPARO CON MOUSE
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {   
        	float startX = spr.getX() + spr.getOriginX();
            float startY = spr.getY() + spr.getOriginY();
            estrategia.disparar(startX, startY, angleRadians, juego);
        }  
    }
      
    public boolean checkCollision(Rectangle area) {
    	// Ajuste hitbox
    	float margen = 15f;
    	Rectangle hitboxReducido = new Rectangle(
    	        spr.getX() + margen, 
    	        spr.getY() + margen, 
    	        spr.getWidth() - (margen * 2), 
    	        spr.getHeight() - (margen * 2)
    	    );
        if(!herido && hitboxReducido.overlaps(area)){
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    public boolean estaDestruido() {
       return destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public void aumentarDano(int cantidad) {
        this.danoDisparo += cantidad;
    }
    
    public void setDano(int d) {
        this.danoDisparo = d;
    }
    
    public int getDanoDisparo() {
        return this.danoDisparo;
    }
    
    public int getVidas() {return vidas;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
	public void setEstrategia(EstrategiaDisparo nuevaEstrategia) {
        this.estrategia = nuevaEstrategia;
    }
}