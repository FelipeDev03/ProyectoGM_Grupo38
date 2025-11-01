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
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setSize(64, 64); // tamaño visible
    	spr.setOriginCenter();
    	spr.setPosition(x, y);
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
           spr.setX(spr.getX()+MathUtils.random(-2,2));
           spr.draw(batch); 
           spr.setX(spr.getX()); 
           tiempoHerido--;
           if (tiempoHerido<=0) herido = false;
         }
        
        // LÓGICA DE DISPARO CON MOUSE
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {   
            
            float velBala = 10.0f;
            
            float velX = MathUtils.cos(angleRadians) * velBala;
            float velY = MathUtils.sin(angleRadians) * velBala;

            float startX = spr.getX() + spr.getOriginX();
            float startY = spr.getY() + spr.getOriginY();

            Bullet bala = new Bullet(startX, startY, velX, velY, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }
      
    public boolean checkCollision(Rectangle area) {
        if(!herido && area.overlaps(spr.getBoundingRectangle())){
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
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {return vidas;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}