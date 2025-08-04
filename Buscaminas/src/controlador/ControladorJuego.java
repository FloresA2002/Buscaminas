package controlador;

import modelo.*;
import vista.VistaConsola;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controlador principal del juego Buscaminas.
 * Gestiona la interacción con el usuario, las acciones sobre el tablero
 * y la persistencia de partidas.
 */
public class ControladorJuego {
    private Tablero tablero;
    private VistaConsola vista;
    private boolean juegoActivo;

    /** Inicializa el juego con un tablero nuevo y la vista en consola */
    public ControladorJuego() {
        tablero = new Tablero();
        vista = new VistaConsola();
        juegoActivo = true;
    }

    /**
     * Inicia el bucle principal del juego, solicitando comandos al usuario.
     * Comandos disponibles:
     * - d A5 : descubrir casilla
     * - m B7 : marcar casilla
     * - g archivo.dat : guardar partida
     * - c archivo.dat : cargar partida
     */
    public void iniciarJuego() {
        Scanner sc = new Scanner(System.in);

        while (juegoActivo) {
            vista.mostrarTablero(tablero.getTablero());
            System.out.println("Comandos: d A5(descubrir), m B7(marcar), g archivo.dat(guardar), c archivo.dat(cargar)");
            
            String comando = sc.next().toLowerCase();

            try {
                if (comando.equals("d") || comando.equals("m")) {
                    String coordenada = sc.next().toUpperCase();
                    char letraFila = coordenada.charAt(0);
                    int fila = letraFila - 'A';
                    int columna = Integer.parseInt(coordenada.substring(1));

                    if (comando.equals("d")) {
                        boolean seguir = tablero.descubrirCasilla(fila, columna);
                        if (!seguir) {
                            System.out.println("¡BOOM! Pisaste una mina. Game Over.");
                            vista.mostrarTablero(tablero.getTablero());
                            juegoActivo = false;
                        } else if (tablero.juegoGanado()) {
                            System.out.println("¡Felicidades! Has ganado.");
                            vista.mostrarTablero(tablero.getTablero());
                            juegoActivo = false;
                        }
                    } else {
                        tablero.marcarCasilla(fila, columna);
                    }

                } else if (comando.equals("g")) { 
                    String archivo = sc.next();
                    guardarJuego(archivo);
                } else if (comando.equals("c")) { 
                    String archivo = sc.next();
                    cargarJuego(archivo);
                } else {
                    System.out.println("Comando inválido.");
                    sc.nextLine(); 
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada inválida. Usa formato d A5 o m B7.");
                sc.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (CasillaYaDescubiertaException e) {
                System.out.println("Atención: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: La columna debe ser un número válido.");
            }
        }
        sc.close();
    }

    /**
     * Guarda la partida en un archivo binario usando serialización.
     * @param archivo nombre o ruta del archivo donde se guardará el tablero
     */
    private void guardarJuego(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(tablero);
            System.out.println("Partida guardada en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Carga una partida previamente guardada.
     * @param archivo nombre o ruta del archivo binario a cargar
     */
    private void cargarJuego(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            tablero = (Tablero) ois.readObject();
            System.out.println("Partida cargada desde " + archivo);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }
}
