package DesplegarPagina;

import ModificarXml.MoidificarXml;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.awt.*;
import java.net.URI;

public class DesplegarPagina {

    private  Server server = new Server(5000);

    public void despliegue(String pagina){

        // Configurar el manejador de recursos para servir archivos estáticos
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/pagina/"+pagina+".html"); // Directorio donde se encuentran los archivos HTML
        resourceHandler.setDirectoriesListed(true);

        // Agregar el manejador predeterminado para manejar las solicitudes que no coinciden con los manejadores existentes
        DefaultHandler defaultHandler = new DefaultHandler();

        // Crear una lista de manejadores y agregar los manejadores configurados
        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(defaultHandler);

        // Asignar la lista de manejadores al servidor
        server.setHandler(handlers);

        // Iniciar el servidor
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Servidor Jetty iniciado en http://localhost:5000/");
        String url = "http://localhost:5000/"; // URL a la que deseas que el navegador se abra

        try {
            // Verificar si el soporte para el manejo de escritorio está disponible
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                // Abrir la URL en el navegador predeterminado
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Navegador predeterminado abierto en " + url);
            } else {
                System.out.println("No se puede abrir el navegador predeterminado.");
            }
        } catch (Exception e) {
            System.err.println("Error al abrir el navegador predeterminado: " + e.getMessage());
        }
        // Esperar a que el servidor se detenga
        try {
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void detenerServidor( ) throws Exception {
        if (server != null) {
            server.stop();
            System.out.println("Servidor Jetty detenido.");
        }
    }



}
