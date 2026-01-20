import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMensaje {

    // El puerto que utilizará el servidor para comunicarse
    private int puerto;
    // Un "flag" que indique que el servidor está iniciado o apagado
    private boolean prendido;
    // El socket de procesamiento para el servidor
    private ServerSocket socketServidor;

    /**
     * Constructor del servidor de mensajes
     * @param puerto Que se asigna al momento de crear el servidor
     * @throws IOException Una excepción que ocurre por conflicto de puertos
     */
    public ServidorMensaje(int puerto) throws IOException {
        // Asignar el puerto correspondiente
        this.puerto = puerto;
        // Crear el socket de servidor utilizando el puerto asignado
        this.socketServidor = new ServerSocket(this.puerto);
        // Imprimir un mensaje de inicio
        System.out.println(String.format("El servidor ha sido iniciado en el puerto %d", this.puerto));
    }

    /**
     * Método que permite iniciar el servidor
     */
    public void iniciarServidor() {
        // Cambiar el estado a encendido
        this.prendido = true;
        // Agregar un mensaje para indicar el estado
        System.out.println("El servidor ha sido iniciado..." );

        // El proceso es repetitivo mientras el servidor esté prendido, usemos un while
        while (this.prendido) {
            try {
                // Aceptamos cualquier conexión entrante
                Socket conexionEntrante = this.socketServidor.accept();

                // Podemos imprimir información de la conexión entrante
                String hostCliente = conexionEntrante.getInetAddress().getHostName();
                System.out.println(String.format("Conexion desde: %s", hostCliente));

                // Capturamos el flujo de entrada de la conexión
                InputStream flujoEntrada = conexionEntrante.getInputStream();

                // Para leer flujos de entrada, definimos un componente auxiliar
                InputStreamReader isr = new InputStreamReader(flujoEntrada);

                // Para procesar flujos fácilmente a través de las cadenas usando un buffer...
                BufferedReader br = new BufferedReader(isr);

                // Realizamos la lectura del mensaje
                String mensaje = br.readLine();

                // Imprimimos el mensaje
                System.out.println(String.format("Mensaje recibido: %s", mensaje));

                // Cerramos los flujos para esta conexión
                br.close();
                isr.close();
                flujoEntrada.close();
                conexionEntrante.close();

            } catch (IOException e) {
                e.printStackTrace();
                // Ante una excepción, apagamos el servidor
                this.prendido = false;
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServidorMensaje miServidor = new ServidorMensaje(2999);
            miServidor.iniciarServidor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
