package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;


public class PantallaGameOver implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Viewport viewport;
	private int rondaFinal;

	public PantallaGameOver(SpaceNavigation game, int rondaFinal) {
		this.game = game;
		this.rondaFinal = rondaFinal;
        
		camera = new OrthographicCamera();
		viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		viewport.apply();
		game.getBatch().begin();
		boolean nuevoRecord = (rondaFinal >= game.getHighRonda());
		game.getFont().draw(game.getBatch(), "Game Over !!!", 
				0, 500, SpaceNavigation.WORLD_WIDTH, Align.center, false);
		
		game.getFont().draw(game.getBatch(), "Ronda Alcanzada: " + rondaFinal,
                0, 400, SpaceNavigation.WORLD_WIDTH, Align.center, false);
		
		if (nuevoRecord) {
            game.getFont().draw(game.getBatch(), "¡Nuevo Record!",
                0, 350, SpaceNavigation.WORLD_WIDTH, Align.center, false);
        }
		game.getFont().draw(game.getBatch(), "Haz click en cualquier lado para reintentar ...", 
                0, 250, SpaceNavigation.WORLD_WIDTH, Align.center, true);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,10,1,250,150);
			game.setScreen(ss);
			dispose();
		}
	}
 
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
   
}