public class ParoImpar extends Jugador implements Runnable{
    public ParoImpar(int id, Ruleta ru) {
        super(id, ru);
        ganancias=20;
    }


    @Override
    public boolean apostar(){
       synchronized (this){
        try {
            numeroapostado = (int) (Math.random() * 2) ;

            saldo -= 10;

            System.out.print("el jugador "+id+" a apostado al ");
            if (numeroapostado==0){
                System.out.print("el jugador "+id+" a apostado al par.");
            }
            else {
                System.out.print("el jugador "+id+" a apostado al impar.");
            }
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       }
        return false;
    }
    @Override
    public void run() {
        apostar();
    }
}
