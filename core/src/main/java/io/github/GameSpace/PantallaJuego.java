package io.github.GameSpace;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int cantAsteroides;
	
	private Nave4 nave;
	
	private  ArrayList<Zombie> zombies = new ArrayList<>(); 
	private  ArrayList<Bullet> balas = new ArrayList<>();
	
	private Texture backgroundTexture;
	
	private int zombisPorOleada;        
	private int zombisGenerados;        
	private float tiempoEntreSpawns = 1.0f; 
	private float spawnTimer;           
	private Random r;                   

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);
		
		// --- Cargar Assets (GM1.2) ---
		// (Asegúrate de tener estos archivos en tu carpeta 'assets')
		backgroundTexture = new Texture(Gdx.files.internal("fondo_zombie.png"));
		
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("zombie_muerte.wav"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3")); 
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    
		nave = new Nave4(SpaceNavigation.WORLD_WIDTH / 2 - 22, 
                SpaceNavigation.WORLD_HEIGHT / 2 - 22, 
                new Texture(Gdx.files.internal("survivor.png")), 
				 Gdx.audio.newSound(Gdx.files.internal("jugador_herido.wav")), 
				 new Texture(Gdx.files.internal("bala.png")), 
				 Gdx.audio.newSound(Gdx.files.internal("disparo.wav"))); 
		
		nave.setVidas(vidas);
		
		this.r = new Random(); 
        this.zombisPorOleada = cantAsteroides; 
        this.zombisGenerados = 0;
        this.spawnTimer = 0; 
	}
    
	private void generarUnZombi() {
	    float angulo = r.nextFloat() * 360f;
	    float radioSpawn = 500f; 
	    
	    float spawnX = SpaceNavigation.WORLD_WIDTH / 2f + MathUtils.cosDeg(angulo) * radioSpawn;
	    float spawnY = SpaceNavigation.WORLD_HEIGHT / 2f + MathUtils.sinDeg(angulo) * radioSpawn;
	    
	    float velocidadBaseZombi = velXAsteroides;
		
        float velocidadMejorada = velocidadBaseZombi;
        int saludMejorada = 1 + ronda; 
        
        Texture zombieTexture = new Texture(Gdx.files.internal("zombi.png"));

        // Spawn Tanque
        // 15% de chance, a partir de la ronda 5)
        if (ronda >= 5 && r.nextFloat() < 0.15f) {
            ZombieTanque zt = new ZombieTanque((int)spawnX, (int)spawnY, velocidadMejorada, zombieTexture);
            // Los tanques tienen el triple de vida que uno normal
            zt.health = saludMejorada * 3; 
            zombies.add(zt);
        } 
        
        // Spawn rapidos
        // 0% de chance, a partir de la ronda 3)
        else if (ronda >= 3 && r.nextFloat() < 0.20f) { // <-- ¡CORREGIDO!
            ZombieRapido zr = new ZombieRapido((int)spawnX, (int)spawnY, velocidadMejorada, zombieTexture);
            zr.health = (int)(saludMejorada * 0.5f);
            zombies.add(zr);
        } 
        
        // Spawn normal
        else {
            ZombieNormal zn = new ZombieNormal((int)spawnX, (int)spawnY, velocidadMejorada, zombieTexture);
            zn.health = saludMejorada;
            zombies.add(zn);
        }
	}

	public void dibujaEncabezado() {
		game.getFont().getData().setScale(2f);		

		game.getFont().draw(batch, "Vidas: "+nave.getVidas(), 10, 70); 
		game.getFont().draw(batch, "Ronda: "+ronda, 10, 30);            
		
		game.getFont().draw(batch, "Puntos:"+this.score, SpaceNavigation.WORLD_WIDTH-150, 30);
		game.getFont().draw(batch, "Ronda Record: "+game.getHighRonda(), SpaceNavigation.WORLD_WIDTH/2-100, 30);
		
		int zombisRestantes = (zombisPorOleada - zombisGenerados) + zombies.size();
		
		game.getFont().draw(batch, "Zombis Restantes: "+zombisRestantes, 
			0, 
			SpaceNavigation.WORLD_HEIGHT - 20,
			SpaceNavigation.WORLD_WIDTH,
			Align.center,
			false);
	}
	
	@Override
	public void render(float delta) {
		  ScreenUtils.clear(0, 0, 0, 1); 
		  
		  camera.update();
		  viewport.apply();
		  
		  //LÓGICA 
		  
		  // Generar zombis
		  if (zombisGenerados < zombisPorOleada) {
              spawnTimer += delta; 
              if (spawnTimer >= tiempoEntreSpawns) {
                  generarUnZombi();
                  zombisGenerados++;
                  spawnTimer = 0f; 
              }
          }
		  
	      if (!nave.estaHerido()) {
		      
			  // Lógica de Balas
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
					boolean balaRemovida = false;
		            for (int j = 0; j < zombies.size(); j++) {    
					  Zombie z = zombies.get(j);
		              if (b.checkCollision(z.getArea())) { 
		            	 z.hit(1);
                         if (!z.isActive()) { 
                            explosionSound.play();
                            z.onDeath(); 
                            zombies.remove(j);
                            j--;
                            score +=10;
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
		      // Lógica de Zombis
		      for (Zombie z : zombies) {
		          z.update();
		      }
	      }
		  
	      // Lógica de Colisión Nave vs Zombi
	      for (int i = 0; i < zombies.size(); i++) {
	    	    Zombie z = zombies.get(i);
	            if (nave.checkCollision(z.getArea())) { 
	            	 zombies.remove(i);
	            	 i--;
                }   	  
  	      }
		  
		  // --- DIBUJADO
          batch.setProjectionMatrix(camera.combined);
          batch.begin();
		  
		  // 1. FONDO
		  batch.draw(backgroundTexture, 0, 0, SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT);
		  
		  // 2. ZOMBIS
	      for (Zombie z : zombies) {
	    	    z.draw(batch);
  	      }
		  
		  // 3. BALAS
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
		  
		  // 4. SOBREVIVIENTE
	      nave.draw(batch, this);
		  
		  // 5. TEXTO (UI)
		  dibujaEncabezado();
		  
	      batch.end();
		  
		  
		  
		  // --- 3. LÓGICA DE FIN DE JUEGO / RONDA ---
	      if (nave.estaDestruido()) {
  			if (ronda > game.getHighRonda())
  				game.setHighRonda(ronda);
	    	Screen ss = new PantallaGameOver(game, ronda);
  			game.setScreen(ss);
  			dispose();
  		  }
		  
	      if (zombisGenerados == zombisPorOleada && zombies.size() == 0) {
            Screen ss = new PantallaTienda(game, ronda, nave, score, velXAsteroides, cantAsteroides);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); 
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		this.explosionSound.dispose();
		this.gameMusic.dispose();
		this.backgroundTexture.dispose();
	}
   
}