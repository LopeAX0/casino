public class Del1al36 extends Jugador {

    public Del1al36(int id) {
        super(id);
        ganancias=360;
    }
    @Override
    public void apostar(){
        try {
            numeroapostado = (int) (Math.random() * 36) + 1;

            saldo -= 10;
            System.out.println("el jugador "+id+" a apostado al numero "+numeroapostado);
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
