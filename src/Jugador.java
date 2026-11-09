public abstract class Jugador {
    int id;
    int saldo=20;
    int ganancias;
    int pago=10;
    int numeroapostado;
    Ruleta RULETA;

    public Jugador(int id,Ruleta ru) {
        this.id = id;
        this.RULETA=ru;
    }

    public boolean apostar()  {
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getGanancias() {
        return ganancias;
    }

    public void setGanancias(int ganancias) {
        this.ganancias = ganancias;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getNumeroapostado() {
        return numeroapostado;
    }

    public void setNumeroapostado(int numeroapostado) {
        this.numeroapostado = numeroapostado;
    }

    public abstract void run();
}
