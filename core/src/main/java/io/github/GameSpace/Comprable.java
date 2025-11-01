package io.github.GameSpace;

public interface Comprable {
    String getNombre();         
    String getDescripcion();    
    int getCosto();             
    void aplicarMejora(Nave4 nave, PantallaJuego juego); 
    boolean esMejoraUnica();    
}