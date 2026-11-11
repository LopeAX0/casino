package Ejerciciorepaso;

public class Consumidor implements Runnable{
    int id;
    Documento escribir;
    public Consumidor(int id, Documento escribir) {
        this.id = id;
        this.escribir = escribir;
    }
    @Override
    public void run() {
        boolean continuar = true;
        while(continuar){
            escribir.lellendo(id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
