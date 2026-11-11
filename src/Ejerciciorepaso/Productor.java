package Ejerciciorepaso;

import java.io.IOException;

public class Productor implements Runnable{
    int id;
    Documento escribir;
    public Productor(int id, Documento documento) {
        this.id = id;
        this.escribir = documento;
    }

    @Override
    public void run() {
        boolean encontrado = false;
        while(encontrado == false) {
            try {

                escribir.escribir(this.id);
Thread.sleep(1000);


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
