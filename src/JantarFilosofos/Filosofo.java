package JantarFilosofos;

public class Filosofo extends Thread {
    private int identificador;

    final static int PENSANDO = 0;
    final static int FAMINTO  = 1;
    final static int COMENDO  = 2;

    public static final int TIME = 3000;

    public String message = "";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Filosofo (String nome, int identificador) {
        super(nome);
        this.identificador = identificador;
    }

    public void setEstaFaminto () {
        Ambiente.estado[this.identificador] = FAMINTO;
        setMessage(ANSI_RED + "[" + this.identificador + "] O Filósofo " + getName() + " está FAMINTO!" + ANSI_RESET);
    }

    public void Comendo () {
        Ambiente.estado[this.identificador] = COMENDO;
        setMessage(ANSI_GREEN + "[" + this.identificador+ "] O Filósofo " + getName() + " está COMENDO!" + ANSI_RESET);

        try {
            Thread.sleep(TIME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Pensando () {
        Ambiente.estado[this.identificador] = 0;
        setMessage(ANSI_CYAN + "[" + this.identificador + "] O Filósofo " + getName() + " está PENSANDO!" + ANSI_RESET);

        try {
            Thread.sleep(TIME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SoltarGarfo () {
        Ambiente.mutex.decrementar();
        Pensando();

        Ambiente.filosofo[VizinhoEsquerda()].TentarObterGarfos();
        Ambiente.filosofo[VizinhoDireita()].TentarObterGarfos();

        Ambiente.mutex.incrementar();
    }

    public void PegarGarfo () {
        Ambiente.mutex.decrementar();
        setEstaFaminto();

        TentarObterGarfos();

        Ambiente.mutex.incrementar();
        Ambiente.semaforos[this.identificador].decrementar();
    }

    public void TentarObterGarfos() {
        if (Ambiente.estado[this.identificador] == FAMINTO &&
                Ambiente.estado[VizinhoEsquerda()] != COMENDO &&
                Ambiente.estado[VizinhoDireita()] != COMENDO) {
            Comendo();
            Ambiente.semaforos[this.identificador].incrementar();
        }
    }

    @Override
    public void run () {
        try {
            Pensando();

            do {
                PegarGarfo();
                Thread.sleep(TIME);
                SoltarGarfo();
            } while (true);
        }

        catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public int VizinhoDireita() {
        return (this.identificador + 1) % 5;
    }

    public int VizinhoEsquerda() {
        if (this.identificador == 0) {
            return 4;
        }

        return (this.identificador - 1) % 5;
    }
}