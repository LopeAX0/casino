public class ParoImpar extends Jugador implements Runnable{
    public ParoImpar(int id, Ruleta ru) {
        super(id, ru);
        ganancias=20;
    }
    String eleccion;


    @Override
    public boolean apostar(){
       synchronized (RULETA){
        try {

            if (RULETA.salida) {
                return false;
            }

            if (saldo < 10) {
                return false;
            } RULETA.setJugando(1);
            numeroapostado = (int) (Math.random() * 2) ;

            saldo -= 10;
            RULETA.setDinero(10);


            if (numeroapostado==0){
                eleccion="Par";
                System.out.println("el jugador ParOImpar"+id+" a apostado al par.");
            }
            else {
                eleccion="Impar";
                System.out.println("el jugador ParOImpar"+id+" a apostado al impar.");
            }
            RULETA.wait();

            if (RULETA.salida) {
                return false;
            }

            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       }

    }
    @Override
    public void run() {
        while (!RULETA.salida) {
            boolean puede = apostar();

            if (!puede) {

                // Si el juego ya terminó por quiebra, no imprimimos que se quedó sin dinero por error
                if (!RULETA.salida) {
                    System.out.println("El jugador ParOImpar" + id + " se ha quedado sin dinero");
                    RULETA.setJugando(-1);
                    RULETA.esperado-=1;
                }
                break;
            }

            // Si la ruleta salió mientras esperábamos, rompemos el bucle
            if (RULETA.salida) {
                break;
            }
            String salido;
            if (RULETA.numero==0) {
                salido = "ignorar";
            }
            else if (RULETA.numero%2==0) {
                salido = "Par";
            }
            else {
                salido = "Impar";
            }

            // Cuando RULETA.wait() se desbloquea, el hilo continúa aquí:
            if (eleccion.equals(salido)) {
                RULETA.pagarPremio(this, ganancias);
                System.out.println("¡¡ El jugador ParOImpar " + id + " HA GANADO con un número " + eleccion + " !! Saldo actual: " + saldo);

            }
              else {
                System.out.println("El jugador ParOImpar " + id + " perdió. Salió el " + RULETA.numero + ". Saldo actual: " + saldo);
            }
            RULETA.setJugando(-1);
        }
    }
}
