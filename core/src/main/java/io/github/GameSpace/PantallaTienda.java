package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Collections;

public class PantallaTienda implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private MejoraDano mejoraDano;
    private MejoraVida mejoraVida;
    private String mensajeFeedback = "";
    private float tiempoMensaje = 0;
    
    // Listas mejoras
    private ArrayList<Comprable> todasLasMejoras;
    private ArrayList<Comprable> mejorasEnTienda;
    
    // Para la UI
    private GlyphLayout layout = new GlyphLayout();
    private Rectangle botonMejora1;
    private Rectangle botonMejora2;
    private Rectangle botonContinuar;


    public PantallaTienda(SpaceNavigation game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);

        // En caso de agregar mejoras en posteriores updates
        todasLasMejoras = new ArrayList<>();
        mejoraVida = new MejoraVida();
        todasLasMejoras.add(mejoraVida);
        mejoraDano = new MejoraDano();
        todasLasMejoras.add(mejoraDano);
        
        // Seleccionar 2 aleatorias para mostrar
        Collections.shuffle(todasLasMejoras);
        mejorasEnTienda = new ArrayList<>();
        mejorasEnTienda.add(todasLasMejoras.get(0));
        
        if (todasLasMejoras.size() > 1) {
            mejorasEnTienda.add(todasLasMejoras.get(1));
        }
        
        // Definir áreas de los botones
        botonMejora1 = new Rectangle(50, 200, 300, 200);
        botonMejora2 = new Rectangle(450, 200, 300, 200);
        botonContinuar = new Rectangle(300, 50, 200, 50);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1); // Fondo oscuro
        camera.update();
        viewport.apply();
        game.getBatch().setProjectionMatrix(camera.combined);
        // Obtenemos al Manager
        GameManager gm = GameManager.getInstancia();
        game.getBatch().begin();
        
        // Títulos
        game.getFont().draw(game.getBatch(), "Ronda " + (gm.getRonda()) + " Superada!", 
            0, 550, SpaceNavigation.WORLD_WIDTH, Align.center, false);
        game.getFont().draw(game.getBatch(), "Puntos: " + gm.getScore(), 
            0, 500, SpaceNavigation.WORLD_WIDTH, Align.center, false);
        if (tiempoMensaje > 0) {
            layout.setText(game.getFont(), mensajeFeedback, com.badlogic.gdx.graphics.Color.YELLOW, SpaceNavigation.WORLD_WIDTH, Align.center, false);
            
            game.getFont().draw(game.getBatch(), layout, 0, 450);
            
            tiempoMensaje -= delta;
        }

        // --- Dibujar Mejoras
        
        // Botón 1
        if (mejorasEnTienda.size() > 0) {
            Comprable mejora1 = mejorasEnTienda.get(0);
            String texto1 = mejora1.getNombre() + "\nCosto: " + mejora1.getCosto() + "\n" + mejora1.getDescripcion();
            layout.setText(game.getFont(), texto1, com.badlogic.gdx.graphics.Color.WHITE, botonMejora1.width, Align.center, true);
            game.getFont().draw(game.getBatch(), layout, botonMejora1.x, botonMejora1.y + botonMejora1.height - 20);
        }

        // Botón 2
        if (mejorasEnTienda.size() > 1) {
            Comprable mejora2 = mejorasEnTienda.get(1);
            String texto2 = mejora2.getNombre() + "\nCosto: " + mejora2.getCosto() + "\n" + mejora2.getDescripcion();
            layout.setText(game.getFont(), texto2, com.badlogic.gdx.graphics.Color.WHITE, botonMejora2.width, Align.center, true);
            game.getFont().draw(game.getBatch(), layout, botonMejora2.x, botonMejora2.y + botonMejora2.height - 20);
        }

        // Botón Continuar
        layout.setText(game.getFont(), "Siguiente Ronda", com.badlogic.gdx.graphics.Color.GREEN, botonContinuar.width, Align.center, true);
        game.getFont().draw(game.getBatch(), layout, botonContinuar.x, botonContinuar.y + botonContinuar.height - 10);
        
        game.getBatch().end();

        // Lógica de Input
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // Convertir coordenadas de pantalla a coordenadas del mundo del juego
            float clickX = Gdx.input.getX();
            float clickY = Gdx.input.getY();
            // Por ahora, usamos la conversión simple si no hay reescalado:
            clickY = Gdx.graphics.getHeight() - clickY;

            if (mejorasEnTienda.size() > 0 && botonMejora1.contains(clickX, clickY)) {
                comprarMejora(mejorasEnTienda.get(0));
            } else if (mejorasEnTienda.size() > 1 && botonMejora2.contains(clickX, clickY)) {
                comprarMejora(mejorasEnTienda.get(1));
            } else if (botonContinuar.contains(clickX, clickY)) {
                iniciarSiguienteRonda();
            }
        }
    }
    
    private void comprarMejora(Comprable mejora) {
    	GameManager gm = GameManager.getInstancia();
    	
        if (gm.getScore() >= mejora.getCosto()) {
        	gm.restarPuntaje(mejora.getCosto());
            mejora.aplicarMejora(null, null);
            
            // Si se compro
            mensajeFeedback = "¡Comprado: " + mejora.getNombre() + "!";
            tiempoMensaje = 2.0f; // El mensaje dura 2 segundos
        } else {
            // Si no alcanza
            mensajeFeedback = "¡Puntos insuficientes! Necesitas " + mejora.getCosto();
            tiempoMensaje = 1.5f; // El mensaje dura 1.5 segundos
        }
    }
    
    private void iniciarSiguienteRonda() {
    	GameManager.getInstancia().avanzarRonda();
        
        Screen ss = new PantallaJuego(game);
        game.setScreen(ss);
        dispose();
    }
    
    // Métodos de Screen que no usamos
    @Override public void show() {}
    @Override public void resize(int width, int height) { viewport.update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}