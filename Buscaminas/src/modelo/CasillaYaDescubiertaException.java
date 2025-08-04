package modelo;

/**
 * Excepción personalizada que se lanza cuando el jugador
 * intenta descubrir una casilla que ya estaba descubierta.
 */
public class CasillaYaDescubiertaException extends Exception {
    /**
     * Crea una nueva excepción con un mensaje específico.
     * @param mensaje descripción del error
     */
    public CasillaYaDescubiertaException(String mensaje) {
        super(mensaje);
    }
}
