public class Juego {
    public static void main(String[] args) throws InterruptedException {
        Ruleta ruleta=new Ruleta();
        Thread hiloRuleta = new Thread(ruleta);

        /*for (int i=0;i<4;i++) {
            Thread b = new Thread(new Del1al36(i + 1, ruleta));
            b.start();
            ruleta.setJugando(1);
        }
       /* for (int i=0;i<4;i++) {
            Thread b = new Thread(new ParoImpar(i + 1, ruleta));
            b.start();
            ruleta.setJugando(1);
        }**/
        for (int i=0;i<4;i++) {
            Thread b = new Thread(new Martingala(i + 1, ruleta));
            b.start();
            ruleta.setJugando(1);
        }

        hiloRuleta.start();




    }
}
