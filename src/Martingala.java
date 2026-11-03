public class Martingala extends Jugador{
    int perdido=0;
    public Martingala(int id) {
        super(id);
        ganancias=360;
    }
    int sigiente=1;
    @Override
    public void apostar(){
        try {
            numeroapostado = (int) (Math.random() * 36) + 1;

                saldo-= 10*sigiente;


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
    public void perder(){
            sigiente+=1;

    }
}
