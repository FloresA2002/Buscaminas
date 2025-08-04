package vista;

import modelo.Casilla;

/**
 * Vista en consola del juego Buscaminas.
 * Se encarga de mostrar el tablero al jugador
 * con los símbolos apropiados para cada estado.
 */
public class VistaConsola {

    /**
     * Muestra el tablero en consola usando los símbolos:
     * - ■ : casilla cubierta
     * - F : casilla marcada como mina
     * - X : mina descubierta
     * - V : casilla vacía sin minas alrededor
     * - Número : cantidad de minas adyacentes
     *
     * @param tablero matriz de casillas a mostrar
     */
    public void mostrarTablero(Casilla[][] tablero) {
        System.out.print("   ");
        for (int i = 0; i < tablero.length; i++) System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < tablero.length; i++) {
            char filaLetra = (char) ('A' + i);
            System.out.print(filaLetra + "  ");
            for (int j = 0; j < tablero[i].length; j++) {
                Casilla c = tablero[i][j];
                if (c.estaMarcada()) {
                    System.out.print("F ");
                } else if (!c.estaDescubierta()) {
                    System.out.print("■ ");
                } else if (c.tieneMina()) {
                    System.out.print("X ");
                } else if (c.getMinasAlrededor() > 0) {
                    System.out.print(c.getMinasAlrededor() + " ");
                } else {
                    System.out.print("V ");
                }
            }
            System.out.println();
        }
    }
}
