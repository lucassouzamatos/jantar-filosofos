package JantarFilosofos;

public class Ambiente extends Thread {

    public static Semaforo mutex = new Semaforo(1);
    public static Semaforo semaforos[] = new Semaforo[5];
    public static int estado[] = new int[5];
    static Filosofo filosofo[] = new Filosofo[5];

    public Ambiente () {
        init();
    }

    public void init () {
        for (int i = 0; i < estado.length; i++) {
            estado[i] = 0;
        }

        Thread.currentThread().setPriority(1);

        filosofo[0] = new Filosofo("Platao", 0);
        filosofo[1] = new Filosofo("Socrates", 1);
        filosofo[2] = new Filosofo("Aristoteles", 2);
        filosofo[3] = new Filosofo("Tales", 3);
        filosofo[4] = new Filosofo("Sofocles", 4);

        semaforos[0] = new Semaforo(0);
        semaforos[1] = new Semaforo(0);
        semaforos[2] = new Semaforo(0);
        semaforos[3] = new Semaforo(0);
        semaforos[4] = new Semaforo(0);

        filosofo[0].start();
        filosofo[1].start();
        filosofo[2].start();
        filosofo[3].start();
        filosofo[4].start();
    }

    @Override
    public void run() {
        try {
            do {
                Thread.sleep(Filosofo.TIME);

                for (int i = 0; i < 50; i++) {
                    System.out.println("\n\n\n");
                }


                for (int i = 0; i < filosofo.length; i++) {
                    System.out.println(filosofo[i].getMessage());
                }
            }

            while(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}