package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {

    public static void main(String[] args) {
        int puerto = 5000;

        System.out.println("Servidor iniciando en puerto " + puerto);

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {

            System.out.println("Servidor escuchando... (esperando cliente)");
            Socket cliente = serverSocket.accept(); // BLOQUEA hasta que se conecte un cliente
            System.out.println("Cliente conectado desde: " + cliente.getInetAddress());

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(cliente.getOutputStream()), true
            );

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("Cliente dice: " + mensaje);

                if ("exit".equalsIgnoreCase(mensaje.trim())) {
                    salida.println("Servidor: conexión cerrada. Bye!");
                    break;
                }

                salida.println("Servidor recibió: " + mensaje + " --  Hola");

            }

            System.out.println("Servidor finalizando comunicación...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
