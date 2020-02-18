package JantarFilosofos;

public class Semaforo {

    protected int contador;
    public Semaforo (int valor) {
        this.contador = valor;
    }

    public synchronized void decrementar () {
        while (this.contador == 0) {
            try {
                wait();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.contador--;
    }

    public synchronized void incrementar () {
        this.contador++;
        notify();
    }
}