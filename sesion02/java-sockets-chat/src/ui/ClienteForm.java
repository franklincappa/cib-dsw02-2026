package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClienteForm extends JFrame {

    private JTextArea area;
    private JTextField input;
    private JButton btnEnviar;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClienteForm() {
        setTitle("Chat - Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        area = new JTextArea();
        area.setEditable(false);

        input = new JTextField();
        btnEnviar = new JButton("Enviar");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);
        bottom.add(btnEnviar, BorderLayout.EAST);

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviar());
        input.addActionListener(e -> enviar()); // Enter envía
    }

    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

        // El servidor pide nombre
        String prompt = in.readLine(); // SYSTEM|Escribe tu nombre:
        String nombre = JOptionPane.showInputDialog(this,
                (prompt != null ? prompt.replace("SYSTEM|", "") : "Nombre"),
                "Ingresar nombre",
                JOptionPane.QUESTION_MESSAGE);

        if (nombre == null || nombre.trim().isEmpty()) nombre = "Anon";
        out.println(nombre);

        area.append("✅ Conectado como " + nombre + "\n");

        Thread lector = new Thread(() -> {
            try {
                String linea;
                while ((linea = in.readLine()) != null) {
                    int sep = linea.indexOf('|');
                    if (sep > 0) {
                        String emisor = linea.substring(0, sep);
                        String msg = linea.substring(sep + 1);
                        area.append(emisor + ": " + msg + "\n");
                    } else {
                        area.append(linea + "\n");
                    }
                }
            } catch (IOException e) {
                area.append("⚠️ Conexión cerrada.\n");
            }
        });
        lector.setDaemon(true);
        lector.start();
    }

    private void enviar() {
        String msg = input.getText().trim();
        if (msg.isEmpty()) return;

        out.println(msg);
        input.setText("");

        if (msg.equalsIgnoreCase("/salir")) {
            try { socket.close(); } catch (Exception ignored) {}
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteForm form = new ClienteForm();
            form.setVisible(true);
            try {
                form.conectar("localhost", 2999);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(form, "No se pudo conectar: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                form.dispose();
            }
        });
    }
}
