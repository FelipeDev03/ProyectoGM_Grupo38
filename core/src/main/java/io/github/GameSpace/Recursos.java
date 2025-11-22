package io.github.GameSpace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Recursos {

    // Variable estática privada que guarda la única instancia
    private static Recursos instancia;

    // Assets
    private Texture fondo;
    private Texture texturaJugador;
    private Texture texturaBala;
    private Texture texturaZombie;
    
    private Sound sonidoExplosion;
    private Sound sonidoHerido;
    private Sound sonidoDisparo;
    private Music musicaFondo;

    // Constructor privado
    private Recursos() {
        cargarAssets();
    }

    // Método de acceso público estático
    public static Recursos getInstancia() {
        if (instancia == null) {
            instancia = new Recursos();
        }
        return instancia;
    }

    // Método auxiliar para cargar todo
    private void cargarAssets() {
        fondo = new Texture(Gdx.files.internal("fondo_zombie.png"));
        texturaJugador = new Texture(Gdx.files.internal("survivor.png"));
        texturaBala = new Texture(Gdx.files.internal("bala.png"));
        texturaZombie = new Texture(Gdx.files.internal("zombi.png"));

        sonidoExplosion = Gdx.audio.newSound(Gdx.files.internal("zombie_muerte.wav"));
        sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("jugador_herido.mp3"));
        sonidoDisparo = Gdx.audio.newSound(Gdx.files.internal("disparo.wav"));
        
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.ogg"));
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.5f);
    }

    // Getters para que otras clases usen los recursos
    public Texture getFondo() { return fondo; }
    public Texture getTexturaJugador() { return texturaJugador; }
    public Texture getTexturaBala() { return texturaBala; }
    public Texture getTexturaZombie() { return texturaZombie; }
    
    public Sound getSonidoExplosion() { return sonidoExplosion; }
    public Sound getSonidoHerido() { return sonidoHerido; }
    public Sound getSonidoDisparo() { return sonidoDisparo; }
    public Music getMusicaFondo() { return musicaFondo; }

    // Método para limpiar memoria al cerrar el juego
    public void dispose() {
        fondo.dispose();
        texturaJugador.dispose();
        texturaBala.dispose();
        texturaZombie.dispose();
        sonidoExplosion.dispose();
        sonidoHerido.dispose();
        sonidoDisparo.dispose();
        musicaFondo.dispose();
    }
}