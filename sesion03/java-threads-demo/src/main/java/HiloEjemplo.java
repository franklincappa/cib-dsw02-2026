
package main.java;

import java.util.concurrent.ThreadLocalRandom;

public class HiloEjemplo extends Thread {

    @Override
    public void run() {
        try {
            int randomNum = ThreadLocalRandom.current().nextInt(2000);
            System.out.println("Hilo - Inicio");
            Thread.sleep(randomNum);
            System.out.println("Hilo - Termina proceso");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}