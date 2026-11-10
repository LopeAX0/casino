public class Martingala extends Jugador implements Runnable {
    int sigiente = 1; // Multiplicador de la apuesta (1 = 10€, 2 = 20€, 4 = 40€...)

    public Martingala(int id, Ruleta ru) {
        super(id, ru);
        ganancias = 360; // Ganancia base por cada 10€ apostados
    }

    @Override
    public boolean apostar(){
        synchronized (RULETA) {
            try {
                if (RULETA.salida) {
                    return false;
                }

                int apuestaActual = 10 * sigiente;

                // Comprobamos si tiene saldo suficiente para la apuesta actual de la martingala
                if (saldo < apuestaActual) {
                    return false;
                }
                RULETA.setJugando(1);

                numeroapostado = (int) (Math.random() * 36) + 1;

                saldo -= apuestaActual;                  // Descontamos la apuesta correcta
                RULETA.setDinero(apuestaActual);         // La banca recauda lo apostado

                System.out.println("El jugador martingala " + id + " ha apostado " + apuestaActual + "€ al número " + numeroapostado);

                RULETA.wait();
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

    public void perder(){
        sigiente *= 2; // Duplica el multiplicador si pierde
    }

    public void ganar(){

        sigiente = 1; // Se reinicia el multiplicador a 1 tras ganar
    }

    @Override
    public void run() {
        while (!RULETA.salida) {
            boolean puede = apostar();
            if (!puede) {

                if (!RULETA.salida) {
                    System.out.println("El jugador martingala " + id + " se ha quedado sin dinero");
                    RULETA.esperado-=1;
                }
                break;
            }
            if (RULETA.salida) {
                break;
            }
            // Comprobamos resultado
            if (numeroapostado == RULETA.numero) {
                int premioPagado = sigiente * 360;
                RULETA.pagarPremio(this, premioPagado);
                ganar(); // Actualiza saldo interno y resetea sigiente a 1
                System.out.println("¡¡ El jugador martingala " + id + " HA GANADO con el número " + numeroapostado + " !! Saldo actual: " + saldo);
            } else {
                System.out.println("El jugador martingala " + id + " perdió. Salió el " + RULETA.numero + ". Saldo actual: " + saldo);
                perder();
            }
            RULETA.setJugando(-1);
        }
    }
}