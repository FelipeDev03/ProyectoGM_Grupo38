package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Ball2 {
	private float x;
    private float y;
    private float speed;
    private Sprite spr;

    public Ball2(int x, int y, int size, float speed, Texture tx) {
    	spr = new Sprite(tx);
    	this.x = x; 
 	
        //validar que borde de esfera no quede fuera
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        //validar que borde de esfera no quede fuera
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
        spr.setPosition(this.x, this.y);
        this.speed = speed;
    }
    public void update() {
    	// Definir el objetivo
        float targetX = SpaceNavigation.WORLD_WIDTH / 2f;
        float targetY = SpaceNavigation.WORLD_HEIGHT / 2f;

        // Calcular el ángulo desde el zombi hacia el centro
        float angulo = MathUtils.atan2(targetY - y, targetX - x);

        // Calcular el movimiento en X e Y basado en el ángulo y la velocidad
        float velX = MathUtils.cos(angulo) * speed;
        float velY = MathUtils.sin(angulo) * speed;

        // Aplicar el movimiento
        x += velX;
        y += velY;
        
        spr.setPosition(x, y);
    }
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    public void checkCollision(Ball2 b2) {
    	/*
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
        	// rebote
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
        	setXSpeed(- getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            
            if (getySpeed() ==0) setySpeed(getySpeed() + b2.getySpeed()/2);
            if (b2.getySpeed() ==0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
            setySpeed(- getySpeed());
            b2.setySpeed(- b2.getySpeed());  
        }
        */
    }
}
