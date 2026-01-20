import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteMensaje {

    // Definir el host o dirección del servidor al cual se le enviará el mensaje
    private String host;
    // Puerto de comunicación (mismo del servidor)
    private int puerto;

    /**
     * Un constructor que asigne los valores
     * @param host Que se va asignar (nombre de máquina o IP)
     * @param puerto Para establecer comunicación
     */
    public ClienteMensaje(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Metodo que envía un mensaje utilizando un socket cliente
     * @param mensaje El mensaje que se va enviar
     * @throws UnknownHostException Ocurre cuando el host o dirección ingresado es erróneo
     * @throws IOException Problemas de red (puertos, conflictos, etc.)
     */
    public void enviarMensaje(String mensaje) throws UnknownHostException, IOException {

        // Se crea el socket cliente utilizando el host y el puerto
        Socket socketCliente = new Socket(this.host, this.puerto);

        // Se obtiene el flujo de salida para escribir el mensaje
        OutputStream flujoSalida = socketCliente.getOutputStream();

        // Se define un componente que permite escribir mensaje en flujos
        OutputStreamWriter osw = new OutputStreamWriter(flujoSalida);

        // Se escribe el mensaje (envío)
        PrintWriter escritorMensaje = new PrintWriter(osw, true);
        escritorMensaje.println(mensaje);
        System.out.println("Mensaje enviado!");

        // Se cierran los flujos y conexiones
        escritorMensaje.close();
        osw.close();
        flujoSalida.close();
        socketCliente.close();
    }

    public static void main(String[] args) {
        try {
            ClienteMensaje cliente = new ClienteMensaje("localhost", 2999);
            cliente.enviarMensaje("Hola mundo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
