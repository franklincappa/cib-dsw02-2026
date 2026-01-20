package client;

import java.io.*;
import java.net.Socket;

public class ClienteSocket {

    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;

        System.out.println("Cliente intentando conectar a " + host + ":" + puerto);

        try (Socket socket = new Socket(host, puerto)) {

            BufferedReader teclado = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true
            );

            System.out.println("Conectado. Escribe mensajes (exit para salir):");

            String texto;
            while ((texto = teclado.readLine()) != null) {
                salida.println(texto);
                String respuesta = entrada.readLine();
                System.out.println("Respuesta: " + respuesta);

                if ("exit".equalsIgnoreCase(texto.trim())) break;
            }

            System.out.println("Cliente finalizando...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
