import java.util.Scanner;

public class Ruleta implements Runnable {
    int numero;
    int dinero = 5000;
    int jugando = 0;
    int esperado = 12;
    boolean salida = false;

    public int getJugando() {
        return jugando;
    }

    public synchronized void setJugando(int cantidad) {
        this.jugando += cantidad;
    }

    public int getDinero() {
        return dinero;
    }

    public synchronized void setDinero(int cantidad) {
        this.dinero += cantidad;
    }
    public void tirada() throws InterruptedException {
        synchronized (this) {

            if (esperado <= 0) {
                salida = true;
                notifyAll();
                return;
            }

            while (esperado > jugando && !salida) {
                wait();
            }

            System.out.println("\n--------------------------------------------------");
            System.out.println("La ruleta está girando... Dinero en banca: " + dinero + "€");

            numero = (int) (Math.random() * 37); // Del 0 al 36
            System.out.println("¡Ha salido el número ganador: " + numero + "!");
            System.out.println("\n--------------------------------------------------");


            notifyAll();
        }
    }
    public synchronized void pagarPremio(Jugador jugador, int pagar){
        if (dinero >= pagar){
            dinero -= pagar;
            jugador.saldo+=pagar;
            // Nota: el saldo ya se actualiza en el método ganar o se suma aquí según prefieras
        } else if (dinero < pagar && dinero > 0) {
            dinero = 0;
            System.out.println("\n[FIN DEL JUEGO]: ¡La banca ha quebrado pagando el premio!");
            salida = true;
            notifyAll();
        } else {
            jugador.saldo+=dinero;
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
                Thread.sleep(1500);

                synchronized (this) {
                    if (dinero <= 0) {
                        System.out.println("\n[FIN DEL JUEGO]: ¡La banca ha quebrado! La ruleta se ha quedado sin dinero.");
                        salida = true;
                        notifyAll();
                        break;
                    }
                    if (esperado <= 0) {
                        System.out.println("\n[FIN DEL JUEGO]: Todos los jugadores se han retirado.");
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