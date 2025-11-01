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
    
    // Datos del juego
    private Nave4 nave;
    private int score;
    private int rondaActual;
    private int velXActual;
    private int cantZombisActual;
    
    private ArrayList<Comprable> todasLasMejoras;
    private ArrayList<Comprable> mejorasEnTienda;
    
    // Para la UI
    private GlyphLayout layout = new GlyphLayout();
    private Rectangle botonMejora1;
    private Rectangle botonMejora2;
    private Rectangle botonContinuar;


    public PantallaTienda(SpaceNavigation game, int rondaActual, Nave4 nave, int score, int velXActual, int cantZombisActual) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SpaceNavigation.WORLD_WIDTH, SpaceNavigation.WORLD_HEIGHT, camera);
        
        this.nave = nave;
        this.score = score;
        this.rondaActual = rondaActual;

        this.velXActual = velXActual;
        this.cantZombisActual = cantZombisActual;

        // Inicializar la lista de todas las mejoras posibles
        todasLasMejoras = new ArrayList<>();
        todasLasMejoras.add(new MejoraVida());
        todasLasMejoras.add(new MejoraDano());
        
        // Seleccionar 2 aleatorias para mostrar
        Collections.shuffle(todasLasMejoras);
        mejorasEnTienda = new ArrayList<>();
        mejorasEnTienda.add(todasLasMejoras.get(0));
        // Asegúrate de tener al menos 2 mejoras en "todasLasMejoras" para evitar errores
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

        game.getBatch().begin();
        
        // Títulos
        game.getFont().draw(game.getBatch(), "Ronda " + rondaActual + " Superada!", 
            0, 550, SpaceNavigation.WORLD_WIDTH, Align.center, false);
        game.getFont().draw(game.getBatch(), "Puntos: " + score, 
            0, 500, SpaceNavigation.WORLD_WIDTH, Align.center, false);

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
        if (score >= mejora.getCosto()) {
            score -= mejora.getCosto();
            mejora.aplicarMejora(nave, null); // Pasamos null porque estas mejoras no afectan a PantallaJuego
        }
    }
    
    private void iniciarSiguienteRonda() {
        int proximaRonda = rondaActual + 1;
        // Aumentamos la dificultad para la siguiente ronda
        int nuevaVelocidad = velXActual;
        int nuevosZombis = cantZombisActual + 5;   // Aumenta la cantidad
        
        Screen ss = new PantallaJuego(game, proximaRonda, nave.getVidas(), score, 
                        nuevaVelocidad, nuevaVelocidad, nuevosZombis);
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