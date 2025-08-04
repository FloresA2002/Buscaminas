package buscaminas;

import controlador.ControladorJuego;

/**
 * Clase principal del juego Buscaminas.
 * Inicia el juego en consola llamando al ControladorJuego.
 */
public class main {
    public static void main(String[] args) {
        ControladorJuego juego = new ControladorJuego();
        juego.iniciarJuego();
    }
}
