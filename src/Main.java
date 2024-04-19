
import Servidor.Servidor;
import ServidorP.ServidorP;
import java.io.IOException;

public class    Main {
    public static ServidorP servidorP = new ServidorP();

    public static void main(String[] args) {
        /*Servidor serv = null;
        try {
            serv = new Servidor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Iniciando servidor\n");
        serv.startServer();*/
        servidorP.iniciarServidor();
    }
}