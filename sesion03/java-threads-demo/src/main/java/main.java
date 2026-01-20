package main.java;

public class main {

    public static void main(String[] args) {
        System.out.println("Main - inicio");
        HiloEjemplo hiloEjemplo = new HiloEjemplo();
        hiloEjemplo.start();

        EjecutableEjemplo ejecutableEjemplo = new EjecutableEjemplo();
        Thread thread = new Thread(ejecutableEjemplo);
        thread.start();
        System.out.println("Main - inicio");
    }
}
