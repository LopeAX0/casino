public class Ruleta {
    int numero;

    public void tirada() throws InterruptedException {
        Thread.sleep(3000);
        notifyAll();
        numero=(int) (Math.random()*37);

    }
}
