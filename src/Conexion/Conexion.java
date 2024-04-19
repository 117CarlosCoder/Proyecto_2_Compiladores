package Conexion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion
{
    private final int PUERTO = 8090;
    private final String HOST = "127.0.0.5";
    protected String mensajeServidor;
    protected ServerSocket ss;
    public static Socket cs;
    protected DataOutputStream  salidaCliente;

    public Conexion(String tipo) throws IOException
    {
        if(tipo.equalsIgnoreCase("servidor"))
        {
            ss = new ServerSocket(PUERTO);
            cs = new Socket();
        }
        else
        {
            cs = new Socket(HOST, PUERTO);
        }
    }
}