package ServidorP;

import GenerarArchivoTxt.GenerarArchivoTxt;
import Jcup.parser;
import Jflex.Lexico;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServidorP {
    public static String message ;
    static Selector selector = null;
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

    public void iniciarServidor() {

        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = null;
                    try {
                        socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = null;
                    try {
                        buffer = ByteBuffer.allocate(Math.max(1024, socketChannel.socket().getReceiveBufferSize()));
                    } catch (SocketException e) {
                        throw new RuntimeException(e);
                    }

                    int bytesRead = 0;
                    try {
                        bytesRead = socketChannel.read(buffer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (bytesRead == -1) {
                        try {
                            socketChannel.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        key.cancel();
                        continue;
                    }

                    buffer.flip();
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    message = new String(data).trim();
                    System.out.println("Received message from client: " + message);

                    interpretar(message);
                    // Una vez que se ha recibido y procesado el mensaje, puedes enviar una respuesta al cliente si es necesario



                }
            }
        }
    }

    public static void sendErrorMessage(String errorMessage) throws IOException {
        // Iterate over all registered keys and send the error message to each client
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.wrap(errorMessage.getBytes());
                socketChannel.write(buffer);
            }
        }
    }

}
