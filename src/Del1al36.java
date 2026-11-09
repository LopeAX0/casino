public class Del1al36 extends Jugador implements Runnable {

    public Del1al36(int id, Ruleta ru) {
        super(id, ru);
        ganancias = 360;
    }

    @Override
    public boolean apostar() {
        synchronized (RULETA) {
            try {
                // Si ya se indicó la salida, salimos antes de apostar
                if (RULETA.salida) {
                    return false;
                }

                if (saldo < 10) {
                    return false;
                }
                RULETA.setJugando(1);

                numeroapostado = (int) (Math.random() * 36) + 1;
                saldo -= 10;
                RULETA.setDinero(10); // La banca recauda la apuesta de 10€

                System.out.println("El jugador 1Al36" + id + " ha apostado al número " + numeroapostado);

                RULETA.wait();

                // Si al despertar la ruleta decretó el fin del juego, devolvemos false
                if (RULETA.salida) {
                    return false;
                }

                return true;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
    }

    @Override
    public void run() {
        // Consultamos directamente la variable booleana de la ruleta en cada iteración
        while (!RULETA.salida) {
            boolean puede = apostar();

            if (!puede) {
                // Si el juego ya terminó por quiebra, no imprimimos que se quedó sin dinero por error
                if (!RULETA.salida) {
                    System.out.println("El jugador 1Al36 " + id + " se ha quedado sin dinero");
                    RULETA.setJugando(-1);
                    RULETA.esperado-=1;
                }
                break;
            }

            // Si la ruleta salió mientras esperábamos, rompemos el bucle
            if (RULETA.salida) {
                break;
            }

            // Cuando RULETA.wait() se desbloquea, el hilo continúa aquí:
            if ((numeroapostado%2) == (RULETA.numero%2)) {
                RULETA.pagarPremio(this, ganancias);
                System.out.println("¡¡ El jugador 1Al36" + id + " HA GANADO con el número " + numeroapostado + " !! Saldo actual: " + saldo);
            } else {
                System.out.println("El jugador 1Al36" + id + " perdió. Salió el " + RULETA.numero + ". Saldo actual: " + saldo);
            }
        }
    }
}