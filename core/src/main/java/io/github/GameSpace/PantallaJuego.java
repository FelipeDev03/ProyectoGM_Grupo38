package io.github.GameSpace;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture; // Ya no se usa explícitamente para instanciar, pero se mantiene por si acaso
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;

public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;  
    private Viewport viewport;
    private SpriteBatch batch;
    private int ronda;
    // private float velZombis = 1.5f; // YA NO SE NECESITA AQUI, LO MANEJA LA FABRICA
    
    private Nave4 nave;
    
    private ArrayList<Zombie> zombies = new ArrayList<>(); 
    private ArrayList<Bullet> balas = new ArrayList<>();
    
    private int zombisPorOleada;        
    private int zombisGenerados;        
    private float tiempoEntreSpawns = 1.0f; 
    private float spawnTimer;           
    private Random r;                   

    // VARIABLE PARA LA FABRICA [Abstract Factory Pattern]
    private ZombieFactory zombieFactory;

    public PantallaJuego(SpaceNavigation game) {
        this.game = game;
        GameManager gm = GameManager.getInstancia();

        batch = game.getBatch();
        camera = new OrthographicCamera();  
        viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);
        
        Recursos.getInstancia().getMusicaFondo().play();
        
        this.ronda = gm.getRonda();
        
        // [Abstract Factory] Inicializamos la fábrica concreta con la configuración de la ronda actual
        this.zombieFactory = new FabricaZombiesJuego(this.ronda);

        nave = new Nave4(
                SpaceNavigation.WORLD_WIDTH / 2 - 32, 
                SpaceNavigation.WORLD_HEIGHT / 2 - 32, 
                Recursos.getInstancia().getTexturaJugador(),
                Recursos.getInstancia().getSonidoHerido()
            );
        
        nave.setVidas(gm.getVidasJugador());
        nave.setDano(gm.getDanoJugador());
        
        this.r = new Random(); 
        this.zombisPorOleada = 8 + (this.ronda * 3);
        this.tiempoEntreSpawns = Math.max(0.5f, 1.5f - (this.ronda * 0.05f));
        this.zombisGenerados = 0;
        this.spawnTimer = 0; 
    }
    
    private void generarUnZombi() {
        // Cálculo de posición (se mantiene igual)
        float angulo = r.nextFloat() * 360f;
        float radioSpawn = 500f; 
        
        float spawnX = SpaceNavigation.WORLD_WIDTH / 2f + MathUtils.cosDeg(angulo) * radioSpawn;
        float spawnY = SpaceNavigation.WORLD_HEIGHT / 2f + MathUtils.sinDeg(angulo) * radioSpawn;
        
        // [Abstract Factory] Delegamos la creación a la fábrica
        // La lógica de probabilidades decide QUÉ pedir, pero la fábrica decide CÓMO crearlo.
        
        Zombie nuevoZombie;

        // Spawn Tanque: 15% chance, ronda >= 5
        if (ronda >= 5 && r.nextFloat() < 0.15f) {
             nuevoZombie = zombieFactory.crearZombieTanque((int)spawnX, (int)spawnY);
        } 
        // Spawn Rapido: 20% chance, ronda >= 3
        else if (ronda >= 3 && r.nextFloat() < 0.20f) {
             nuevoZombie = zombieFactory.crearZombieRapido((int)spawnX, (int)spawnY);
        } 
        // Spawn Normal
        else {
             nuevoZombie = zombieFactory.crearZombieNormal((int)spawnX, (int)spawnY);
        }
        
        zombies.add(nuevoZombie);
    }

    // ... El resto del código (render, update, draw) se mantiene igual ...
    
    // [Se mantiene el resto de métodos: dibujaEncabezado, render, show, resize, etc.]
    // Asegúrate de copiar el resto de métodos que no cambian de tu archivo original.
    
    public void dibujaEncabezado() {
        // ... (código original sin cambios) ...
        game.getFont().getData().setScale(2f);
	    GameManager gm = GameManager.getInstancia();
	    int zombisRestantes = (zombisPorOleada - zombisGenerados) + zombies.size();
	    game.getFont().draw(batch, "Ronda: " + gm.getRonda(), 0, SpaceNavigation.WORLD_HEIGHT - 10, SpaceNavigation.WORLD_WIDTH, Align.center, false);
	    game.getFont().draw(batch, "Zombis Restantes: " + zombisRestantes, 0, SpaceNavigation.WORLD_HEIGHT - 50, SpaceNavigation.WORLD_WIDTH, Align.center, false);
	    game.getFont().draw(batch, "Vidas: " + nave.getVidas(), 10, 70); 
	    game.getFont().draw(batch, "Daño actual: "  + nave.getDanoDisparo(), 10, 30); 
	    game.getFont().draw(batch, "Puntos:" + gm.getScore(), SpaceNavigation.WORLD_WIDTH - 150, 30);
	    game.getFont().draw(batch, "Ronda record: " + gm.getHighScore(), SpaceNavigation.WORLD_WIDTH / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        // ... (Código original de render, spawn logic, collisions, etc.)
        // Solo recuerda que generarUnZombi() ahora usa la fábrica.
        
          ScreenUtils.clear(0, 0, 0, 1); 
		  camera.update();
		  viewport.apply();
		  
		  // Generar zombis
		  if (zombisGenerados < zombisPorOleada) {
              spawnTimer += delta; 
              if (spawnTimer >= tiempoEntreSpawns) {
                  generarUnZombi(); // Llama al método modificado
                  zombisGenerados++;
                  spawnTimer = 0f; 
              }
          }
          
          if (!nave.estaHerido()) {
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
					boolean balaRemovida = false;
		            for (int j = 0; j < zombies.size(); j++) {    
					  Zombie z = zombies.get(j);
		              if (b.checkCollision(z.getArea())) { 
		            	  z.hit(b.getDamage());
                         if (!z.isActive()) { 
                            Recursos.getInstancia().getSonidoExplosion().play();
                            z.onDeath(); 
                            zombies.remove(j);
                            j--;
                            GameManager.getInstancia().agregarPuntaje(10);
                         }
                         balas.remove(i);
                         i--;
						 balaRemovida = true; 
                         break; 
		              }   	  
		  	        }
		            if (balaRemovida) continue; 
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; 
		            }
		      }
		      for (Zombie z : zombies) {
		          z.update();
		      }
	      }
	      
	      for (int i = 0; i < zombies.size(); i++) {
	    	    Zombie z = zombies.get(i);
	            if (nave.checkCollision(z.getArea())) { 
	            	 zombies.remove(i);
	            	 i--;
                }   	  
  	      }
  	      
          batch.setProjectionMatrix(camera.combined);
          batch.begin();
          batch.setColor(0.5f, 0.5f, 0.5f, 1f);
          batch.draw(Recursos.getInstancia().getFondo(), 0, 0, SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT);
          batch.setColor(1f, 1f, 1f, 1f);
	      for (Zombie z : zombies) {
	    	    z.draw(batch);
  	      }
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
		  dibujaEncabezado();
	      batch.end();
	      
	      GameManager gm = GameManager.getInstancia();
	      if (nave.estaDestruido()) {
	    	  gm.actualizarHighScore();
	    	Screen ss = new PantallaGameOver(game);
  			game.setScreen(ss);
  			dispose();
  			return;
  		  }
	      else if (!nave.estaDestruido() && zombisGenerados == zombisPorOleada && zombies.size() == 0) {
	    	gm.setVidasJugador(nave.getVidas()); 
            Screen ss = new PantallaTienda(game);
			game.setScreen(ss);
			dispose();
		  }
	      if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
	    	    nave.setEstrategia(new DisparoSimple());
	    	}
	    	if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
	    	    nave.setEstrategia(new DisparoDoble());
	    	}
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    @Override public void show() { Recursos.getInstancia().getMusicaFondo().play(); }
    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}