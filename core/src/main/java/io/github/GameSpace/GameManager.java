package io.github.GameSpace;

public class GameManager {

    private static GameManager instancia;

    // Estado del Juego
    private int score;
    private int ronda;
    private int highScore;
    
    // Estado del Jugador persistente
    private int vidasJugador;
    private int danoJugador;
    
    // Estado de la Tienda
    private int costoDano;
    private int costoVida;

    // Constructor Privado
    private GameManager() {
        highScore = 0;
        resetearJuego();
    }

    public static GameManager getInstancia() {
        if (instancia == null) {
            instancia = new GameManager();
        }
        return instancia;
    }

    // Llamar para empezar nueva partida desde el menu
    public void resetearJuego() {
        score = 0;
        ronda = 1;
        vidasJugador = 3;
        danoJugador = 1;      // Daño base
        costoDano = 250;      // Costo base daño
        costoVida = 150;      // Costo base vida
    }

    // Getters y Setters
    
    public int getScore() { return score; }
    public void agregarPuntaje(int puntos) { this.score += puntos; }
    public void restarPuntaje(int puntos) { this.score -= puntos; }

    public int getRonda() { return ronda; }
    public void avanzarRonda() { this.ronda++; }

    public int getHighScore() { return highScore; }
    public void actualizarHighScore() {
        if (ronda > highScore) highScore = ronda;
    }

    // Jugador
    public int getVidasJugador() { return vidasJugador; }
    public void setVidasJugador(int vidas) { this.vidasJugador = vidas; }

    public int getDanoJugador() { return danoJugador; }
    public void aumentarDano() { this.danoJugador++; }

    // Economía
    public int getCostoDano() { return costoDano; }
    public void aumentarCostoDano(int cantidad) { this.costoDano += cantidad; }

    public int getCostoVida() { return costoVida; }
    public void aumentarCostoVida(int cantidad) { this.costoVida += cantidad; }
}
