public abstract class Jugador {
    int id;
    int saldo=1000;
    int ganancias;
    int pago=10;
    int numeroapostado;

    public Jugador(int id) {
        this.id = id;
    }

    public void apostar()  {
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
}
