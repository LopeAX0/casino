public class ParoImpar extends Jugador{

    public ParoImpar(int id) {
        super(id);
        ganancias=20;
    }
    @Override
    public void apostar(){
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
}
