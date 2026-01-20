package main.java;

import java.util.concurrent.ThreadLocalRandom;

public class EjecutableEjemplo implements Runnable {

    @Override
    public void run() {
        try {
            int randomNum = ThreadLocalRandom.current().nextInt(3000);
            System.out.println("Ejecutable - inicio");
            Thread.sleep(randomNum);
            System.out.println("Ejecutable - termina");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

