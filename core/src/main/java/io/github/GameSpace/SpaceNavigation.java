package io.github.GameSpace;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceNavigation extends Game {
	
	public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 640;
    
	private SpriteBatch batch;
	private BitmapFont font;
	private int highRonda;	

	public void create() {
		highRonda = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
		Screen ss = new PantallaMenu(this);
		this.setScreen(ss);
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHighRonda() {
		return highRonda;
	}

	public void setHighRonda(int highRonda) {
		this.highRonda = highRonda;
	}
	
	

}