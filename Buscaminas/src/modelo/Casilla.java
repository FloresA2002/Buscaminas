package modelo;

import java.io.Serializable;

/**
 * Representa una casilla individual del tablero en el juego Buscaminas.
 * Cada casilla puede contener una mina, estar descubierta, marcada o vacía.
 * Implementa Serializable para permitir guardar y cargar partidas.
 */
public class Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Indica si la casilla contiene una mina */
    private boolean mina;
    /** Indica si la casilla ha sido descubierta */
    private boolean descubierta;
    /** Indica si el jugador ha marcado esta casilla como sospechosa */
    private boolean marcada;
    /** Número de minas adyacentes (incluyendo diagonales) */
    private int minasAlrededor;

    /**
     * Constructor por defecto. Inicializa la casilla como vacía, no descubierta y no marcada.
     */
    public Casilla() {
        this.mina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasAlrededor = 0;
    }

    /** @return true si la casilla contiene una mina */
    public boolean tieneMina() { return mina; }

    /** @return true si la casilla ha sido descubierta */
    public boolean estaDescubierta() { return descubierta; }

    /** @return true si la casilla está marcada por el jugador */
    public boolean estaMarcada() { return marcada; }

    /** @return número de minas alrededor de esta casilla */
    public int getMinasAlrededor() { return minasAlrededor; }

    /** Coloca una mina en la casilla */
    public void colocarMina() { this.mina = true; }

    /** Incrementa en uno el contador de minas alrededor */
    public void incrementarMinasAlrededor() { this.minasAlrededor++; }

    /** Marca la casilla como descubierta */
    public void descubrir() { this.descubierta = true; }

    /** Cambia el estado de marca de la casilla (bandera ON/OFF) */
    public void cambiarMarcada() { this.marcada = !this.marcada; }
}
