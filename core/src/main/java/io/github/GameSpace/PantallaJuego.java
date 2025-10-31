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
	private int velYAsteroides; 
	private int cantAsteroides;
	
	private Nave4 nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	
	// Variables del Controlador de Oleadas
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
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);
		// camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
		nave = new Nave4(SpaceNavigation.WORLD_WIDTH / 2 - 22, // 400 - 22
                SpaceNavigation.WORLD_HEIGHT / 2 - 22, // 320 - 22
                new Texture(Gdx.files.internal("MainShip3.png")),
				 Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
				 new Texture(Gdx.files.internal("Rocket2.png")), 
				 Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
		nave.setVidas(vidas);

		//float velocidadBaseZombi = velXAsteroides;
		
		this.r = new Random(); // Inicializa el generador aleatorio
        this.zombisPorOleada = cantAsteroides; // cantAsteroides es 10
        this.zombisGenerados = 0;
        this.spawnTimer = 0; // Inicia el temporizador
        /*
        //crear asteroides
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	    	Ball2 bb = new Ball2(r.nextInt(SpaceNavigation.WORLD_WIDTH),
  	            50+r.nextInt(SpaceNavigation.WORLD_HEIGHT - 50),
  	            20+r.nextInt(10), velocidadBaseZombi,
  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	  	*/
	}
    
	private void generarUnZombi() {
	    // Elegir un ángulo aleatorio
	    float angulo = r.nextFloat() * 360f;
	    
	    // Definir un radio fuera de la pantalla
	    float radioSpawn = 500f; 
	    
	    // Calcular la posición X e Y en ese círculo, relativo al centro de la pantalla
	    float spawnX = SpaceNavigation.WORLD_WIDTH / 2f + MathUtils.cosDeg(angulo) * radioSpawn;
	    float spawnY = SpaceNavigation.WORLD_HEIGHT / 2f + MathUtils.sinDeg(angulo) * radioSpawn;
	    
	    // Obtener la velocidad base
	    float velocidadBaseZombi = velXAsteroides;
	    
	    // Crear el zombi
	    Ball2 bb = new Ball2((int)spawnX, (int)spawnY, 20, // tamaño 20 (puedes randomizarlo)
	            velocidadBaseZombi, 
	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	    
	    balls1.add(bb);
	    balls2.add(bb);
	}

	public void dibujaEncabezado() {
		game.getFont().getData().setScale(2f);		

		// Vidas y ronda
		game.getFont().draw(batch, "Vidas: "+nave.getVidas(), 10, 70); // Y=70 (Más arriba)
		game.getFont().draw(batch, "Ronda: "+ronda, 10, 30);            // Y=30 (Abajo)
		
		// Puntos y record
		game.getFont().draw(batch, "Puntos:"+this.score, SpaceNavigation.WORLD_WIDTH-150, 30);
		game.getFont().draw(batch, "Ronda Record: "+game.getHighRonda(), SpaceNavigation.WORLD_WIDTH/2-100, 30);
		
		// Zombis Restantes
		int zombisRestantes = (zombisPorOleada - zombisGenerados) + balls1.size();
		game.getFont().draw(batch, "Zombis Restantes: "+zombisRestantes, 
			0, // X=0
			SpaceNavigation.WORLD_HEIGHT - 20,
			SpaceNavigation.WORLD_WIDTH,
			Align.center,
			false);
	}
	
	@Override
	public void render(float delta) {
		  ScreenUtils.clear(0, 0, 0.2f, 1);
		  // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  
		  camera.update();
		  viewport.apply();
		  batch.setProjectionMatrix(camera.combined);
		  
          batch.begin();
		  dibujaEncabezado();
		  if (zombisGenerados < zombisPorOleada) {
              spawnTimer += delta; // Suma el tiempo del frame al temporizador
              
              if (spawnTimer >= tiempoEntreSpawns) {
                  generarUnZombi();
                  zombisGenerados++;
                  spawnTimer = 0f; // Reinicia el temporizador
              }
          }
	      if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : balls1) {
		          ball.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b=balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      
	      if (nave.estaDestruido()) {
  			if (ronda > game.getHighRonda())
  				game.setHighRonda(ronda);
	    	Screen ss = new PantallaGameOver(game, ronda);
  			//ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      batch.end();
	      // nivel completado
	      if (zombisGenerados == zombisPorOleada && balls1.size() == 0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides, velYAsteroides, cantAsteroides+10);
			// ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true); // true = centrar la cámara
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
   
}
