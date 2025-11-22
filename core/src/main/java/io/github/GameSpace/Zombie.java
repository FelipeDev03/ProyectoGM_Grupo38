package io.github.GameSpace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

// Clase abstracta padre
public abstract class Zombie {
    protected float x;
    protected float y;
    protected float speed;
    protected int health;
    protected Sprite spr;
    protected boolean active = true; 

    public Zombie(int x, int y, int size, float speed, int health, Texture tx) {
        spr = new Sprite(tx);
        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);
        
        spr.setSize(size, size); 
        spr.setOriginCenter();

        
        this.speed = speed;
        this.health = health;
    }

    public void update() {
        if (!active) return;
        // objetivo
        float targetX = SpaceNavigation.WORLD_WIDTH / 2f;
        float targetY = SpaceNavigation.WORLD_HEIGHT / 2f;
        // origen del zombi
        float myCenterX = x + spr.getWidth() / 2;
        float myCenterY = y + spr.getHeight() / 2;

        float angulo = MathUtils.atan2(targetY - myCenterY, targetX - myCenterX);
        float angleDegrees = angulo * MathUtils.radiansToDegrees;
        spr.setRotation(angleDegrees - 90);
        float velX = MathUtils.cos(angulo) * speed;
        float velY = MathUtils.sin(angulo) * speed;

        x += velX;
        y += velY;
        
        spr.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        if (!active) return;
        spr.draw(batch);
    }

    public void hit(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.active = false; 
        }
    }

    public boolean isActive() {
        return active;
    }
    
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public abstract void onDeath(); 
}