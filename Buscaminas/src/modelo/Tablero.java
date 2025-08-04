package modelo;

import java.io.Serializable;
import java.util.Random;

/**
 * Representa el tablero del juego Buscaminas.
 * Contiene una matriz de casillas y gestiona la lógica del juego:
 * - Colocación de minas
 * - Cálculo de números
 * - Descubrimiento en cascada
 */
public class Tablero implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Matriz de casillas que conforma el tablero */
    private Casilla[][] tablero;
    /** Cantidad de filas del tablero */
    private int filas = 10;
    /** Cantidad de columnas del tablero */
    private int columnas = 10;
    /** Cantidad total de minas */
    private int totalMinas = 10;
    /** Cantidad de casillas seguras que aún no han sido descubiertas */
    private int casillasSegurasRestantes;

    /**
     * Constructor: Inicializa el tablero, coloca las minas y calcula los números adyacentes.
     */
    public Tablero() {
        tablero = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
                tablero[i][j] = new Casilla();

        colocarMinas();
        calcularNumeros();
        casillasSegurasRestantes = filas * columnas - totalMinas;
    }

    /**
     * Coloca minas aleatoriamente en el tablero sin repetir posiciones.
     */
    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < totalMinas) {
            int x = rand.nextInt(filas);
            int y = rand.nextInt(columnas);
            if (!tablero[x][y].tieneMina()) {
                tablero[x][y].colocarMina();
                minasColocadas++;
            }
        }
    }

    /**
     * Calcula el número de minas alrededor para cada casilla que no tenga mina.
     */
    private void calcularNumeros() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j].tieneMina()) continue;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx;
                        int ny = j + dy;
                        if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                            if (tablero[nx][ny].tieneMina()) {
                                tablero[i][j].incrementarMinasAlrededor();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Descubre la casilla en las coordenadas especificadas.
     * Si la casilla es segura y tiene 0 minas alrededor, activa el descubrimiento en cascada.
     *
     * @param x fila de la casilla
     * @param y columna de la casilla
     * @return true si no se pisa una mina, false si se pierde
     * @throws CasillaYaDescubiertaException si la casilla ya estaba descubierta
     * @throws ArrayIndexOutOfBoundsException si las coordenadas están fuera del tablero
     */
    public boolean descubrirCasilla(int x, int y) throws CasillaYaDescubiertaException {
        if (x < 0 || x >= filas || y < 0 || y >= columnas) {
            throw new ArrayIndexOutOfBoundsException("Coordenadas fuera de rango: (" + x + "," + y + ")");
        }

        Casilla c = tablero[x][y];
        if (c.estaDescubierta()) {
            throw new CasillaYaDescubiertaException("La casilla ya fue descubierta.");
        }

        c.descubrir();
        if (c.tieneMina()) return false;

        casillasSegurasRestantes--;

        if (c.getMinasAlrededor() == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = x + dx;
                    int ny = y + dy;
                    if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas) {
                        try {
                            descubrirCasilla(nx, ny);
                        } catch (CasillaYaDescubiertaException e) {
                            // Ignorar casillas ya descubiertas
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Marca o desmarca una casilla como sospechosa de tener mina.
     * @param x fila de la casilla
     * @param y columna de la casilla
     */
    public void marcarCasilla(int x, int y) {
        tablero[x][y].cambiarMarcada();
    }

    /** @return matriz de casillas del tablero */
    public Casilla[][] getTablero() { return tablero; }

    /** @return true si todas las casillas seguras fueron descubiertas */
    public boolean juegoGanado() { return casillasSegurasRestantes == 0; }
}
