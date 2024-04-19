package Servidor;

import java.io.*;
import java.net.Socket;

import Conexion.Conexion;
import EnvioMensaje.EnvioMensaje;
import GenerarArchivoTxt.GenerarArchivoTxt;
import Jcup.parser;
import Jflex.Lexico;

public class Servidor extends Conexion implements Runnable//Se hereda de conexión para hacer uso de los sockets y demás
{
    public Servidor() throws IOException{super("servidor");} //Se usa el constructor para servidor de Conexion

    private static void interpretar(String path) {

        parser pars;
        try {
            System.out.println("path " + path);
            StringReader stringReader = new StringReader(path);
            pars = new parser(new Lexico(stringReader));
            pars.parse();
        } catch (Exception ex) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println("Causa: "+ex.getCause());
        }
    }


    public void startServer() {
        try {
            // Bucle infinito para mantener el servidor siempre activo
            while (true) {
                System.out.println("Esperando conexión..."); // Esperando conexión
                cs = ss.accept(); // Accept comienza el socket y espera una conexión desde un cliente
                System.out.println("Cliente en línea");

                Thread recepcionThread = new Thread(new RecepcionMensaje(cs));
                recepcionThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error al aceptar la conexión del cliente: " + e.getMessage());
        } /*finally {
            try {
                cs.close(); // Se cierra el socket del servidor
            } catch (IOException e) {
                System.out.println("Error al cerrar el servidor: " + e.getMessage());
            }
        }*/
    }

    private static class RecepcionMensaje implements Runnable {
        private Socket clienteSocket;

        public RecepcionMensaje(Socket socket) {
            this.clienteSocket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                String mensajeServidor;
                StringBuilder mensajeCompleto = new StringBuilder();
                while ((mensajeServidor = entrada.readLine()) != null) {
                    System.out.println("Mensaje del cliente: " + mensajeServidor);
                    mensajeCompleto.append(mensajeServidor + '\n');
                }
                mensajeCompleto.delete(0, 2);
                interpretar(mensajeCompleto.toString());
                // Una vez que se ha recibido y procesado el mensaje, puedes enviar una respuesta al cliente si es necesario

                GenerarArchivoTxt generarArchivoTxt = new GenerarArchivoTxt();
                generarArchivoTxt.generarArchivoTxt(mensajeCompleto.toString(),mensajeCompleto.toString(),mensajeCompleto.toString());
                Thread envioThread = new Thread(new EnvioMensaje(clienteSocket, "Holas"));
                envioThread.start();
                //clienteSocket.close();

            } catch (IOException e) {
                System.out.println("Error al recibir mensaje del cliente: " + e.getMessage());
            } /*finally {
                try {
                    clienteSocket.close(); // Se cierra el socket del cliente
                } catch (IOException e) {
                    System.out.println("Error al cerrar la conexión con el cliente: " + e.getMessage());
                }
            }*/
        }
    }

   /* public void startServer() {
        try {
            // Bucle infinito para mantener el servidor siempre activo
            while (true) {
                System.out.println("Esperando..."); // Esperando conexión

                cs = ss.accept(); // Accept comienza el socket y espera una conexión desde un cliente

                System.out.println("Cliente en línea");

                // Se obtiene el flujo de salida del cliente para enviarle mensajes
                salidaCliente = new DataOutputStream(cs.getOutputStream());


                // Se obtiene el flujo entrante desde el cliente
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

                String mensajeServidor;
                StringBuilder mensajeCompleto = new StringBuilder();
                while ((mensajeServidor = entrada.readLine()) != null) { // Mientras haya mensajes desde el cliente
                    // Se muestra por pantalla el mensaje recibido
                    System.out.println(mensajeServidor);
                    mensajeCompleto.append(mensajeServidor + '\n');
                }


                mensajeCompleto.delete(0, 2);
                interpretar(mensajeCompleto.toString());
                Thread envioThread = new Thread(new EnvioMensaje(cs,"Holaaaa"));
                envioThread.start();
                GenerarArchivoTxt generarArchivoTxt = new GenerarArchivoTxt();
                generarArchivoTxt.generarArchivoTxt(mensajeCompleto.toString());


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Thread envioThread = new Thread(new EnvioMensaje(cs,"Holaaaa"));
            envioThread.start();
        } finally {
            try {
                // Si se produce un error, se cierra el socket del servidor
                ss.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el servidor: " + e.getMessage());
            }
        }


    }*/

    @Override
    public void run() {
        startServer();
    }
}
