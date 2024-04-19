package EnvioMensaje;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EnvioMensaje implements Runnable {
    private Socket clienteSocket;
    private DataOutputStream salidaCliente;
    private String mensaje;


    public EnvioMensaje(Socket socket, String mensaje) {
        this.clienteSocket = socket;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        try {
            salidaCliente = new DataOutputStream(clienteSocket.getOutputStream());
            salidaCliente.writeUTF(mensaje);
            System.out.println("Mesaje del servidor enciado " + mensaje);
            salidaCliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
