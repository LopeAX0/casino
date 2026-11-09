import java.util.Scanner;

public class Ruleta implements Runnable {
    int numero;
    int id;
    int dinero = 5000;
    int jugando = 0;
    boolean salida = false;

    public int getJugando() {
        return jugando;
    }

    public synchronized void setJugando(int jugando) {
        this.jugando += jugando;
    }

    public int getDinero() {
        return dinero;
    }

    public synchronized void setDinero(int dinero) {
        this.dinero += dinero;
    }

    public void tirada() throws InterruptedException {
        synchronized (this) {
            System.out.println("\nLa ruleta está girando... Introduce el número ganador:");
            System.out.println("Dinero en banca: " + dinero);

            numero = (int) (Math.random() * 36) ;
            System.out.println("¡Ha salido el número: " + numero + "!");

            notifyAll();
        }
    }

    public synchronized void pagarPremio(Jugador jugador, int pagar){
        if (dinero >= pagar){
            dinero -= pagar;
            jugador.setSaldo(jugador.getSaldo() + pagar);
        } else if (dinero < pagar && dinero > 0) {
            jugador.setSaldo(jugador.getSaldo() + dinero);
            dinero = 0;
            System.out.println("\n[FIN DEL JUEGO]: ¡La banca ha quebrado pagando el premio!");
            salida = true; // Activamos la bandera de salida global
            notifyAll();   // Despertamos a los hilos bloqueados para que salgan
        } else {
            dinero = 0;
            System.out.println("\n[FIN DEL JUEGO]: ¡La banca se ha quedado a 0!");
            salida = true;
            notifyAll();
        }
    }

    @Override
    public void run() {
        while (!salida) {
            try {
                Thread.sleep(500);

                synchronized (this) {
                    if (dinero <= 0) {
                        System.out.println("\n[FIN DEL JUEGO]: ¡La banca ha quebrado! La ruleta se ha quedado sin dinero.");
                        salida = true;
                        notifyAll(); // Despierta a los jugadores para que salgan del wait()
                        break;
                    }
                    if (jugando <= 0) {
                        System.out.println("\n[FIN DEL JUEGO]: Todos los jugadores se han quedado sin dinero y se han retirado.");
                        salida = true;
                        notifyAll();
                        break;
                    }
                }

                tirada();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}