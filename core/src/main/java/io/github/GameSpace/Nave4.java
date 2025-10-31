package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    //private float xVel = 0;
    //private float yVel = 0;
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
    	spr.setPosition(x, y);
    	spr.setOriginCenter(); // Linea para que rote en centro
    	spr.setBounds(x, y, 45, 45);
    }
    
    public void draw(SpriteBatch batch, PantallaJuego juego){

        // Declara la variable de ángulo
        float angleRadians; 
        
        // Logica apuntado
        // Obtener coordenadas del mouse.
        float mouseX = Gdx.input.getX();
        
        // Invertir el eje Y
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Obtener el centro del jugador
        float playerX = spr.getX() + spr.getOriginX();
        float playerY = spr.getY() + spr.getOriginY();

        // Calcular ángulo
        angleRadians = MathUtils.atan2(mouseY - playerY, mouseX - playerX);
        
        // Aplicar rotación
        float angleDegrees = angleRadians * MathUtils.radiansToDegrees;
        spr.setRotation(angleDegrees - 90);

        // LÓGICA DE DIBUJADO (CONDICIONAL)
        // omprueba si estás herido
        if (!herido) {
            spr.draw(batch);
        } else {
           // Lógica de cuando está herido
           spr.setX(spr.getX()+MathUtils.random(-2,2));
           spr.draw(batch); 
           spr.setX(spr.getX()); // Devuélvelo a su X original
           tiempoHerido--;
           if (tiempoHerido<=0) herido = false;
         }
        
        // LÓGICA DE DISPARO CON MOUSE
        
        // Comprueba si se presionó el botón izq. del mouse
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {   
            
            float velBala = 5.0f;
            
            float velX = MathUtils.cos(angleRadians) * velBala;
            float velY = MathUtils.sin(angleRadians) * velBala;

            // La bala se crea en el centro del jugador
            float startX = spr.getX() + spr.getOriginX();
            float startY = spr.getY() + spr.getOriginY();

            Bullet bala = new Bullet(startX, startY, velX, velY, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }
      
    public boolean checkCollision(Ball2 b) {
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
        	/*
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            // despegar sprites
      /*      int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }   */
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
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
