public class Martingala extends Jugador implements Runnable{
    int perdido=0;
    int sigiente=1;
    public Martingala(int id, Ruleta ru) {
        super(id, ru);
        ganancias=360;
    }


    @Override
    public boolean apostar(){
      synchronized (this) {
          try {
              numeroapostado = (int) (Math.random() * 36) + 1;

              saldo -= 10 * sigiente;


              System.out.print("el jugador " + id + " a apostado al ");
              if (numeroapostado == 0) {
                  System.out.print("el jugador " + id + " a apostado al par.");
              } else {
                  System.out.print("el jugador " + id + " a apostado al impar.");
              }
              wait();
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
      }
        return false;
    }
    public void perder(){
            sigiente+=1;
    }
    public void ganar(){
        saldo+=ganancias*sigiente;
        sigiente=1;
    }
    @Override
    public void run() {
        apostar();
    }
}
