package DesplegarPagina;

import ModificarXml.MoidificarXml;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class DesplegarPagina {

    private  Server server = new Server(5000);
    private int reloadCount = 0;

    public void despliegue(String pagina){

        // Configurar el manejador de recursos para servir archivos estáticos
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/pagina"); // Directorio donde se encuentran los archivos HTML
        resourceHandler.setDirectoriesListed(true);

        // Agregar el manejador predeterminado para manejar las solicitudes que no coinciden con los manejadores existentes
        DefaultHandler defaultHandler = new DefaultHandler();

        // Crear un manejador para registrar las visitas
        VisitaHandler visitaHandler = new VisitaHandler();

        // Crear una lista de manejadores y agregar los manejadores configurados
        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(defaultHandler);
        handlers.addHandler(visitaHandler);


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
        reloadCount += visitaHandler.getVisitas();
        System.out.println("Numero de visitas: " + reloadCount);

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


    static class VisitaHandler extends AbstractHandler {
        private int visitas = 0;

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            // Incrementar el contador de visitas cada vez que se solicita una página
            visitas++;
            System.out.println("asdasdasd " + visitas);
            // Continuar con el manejo de la solicitud
            baseRequest.setHandled(false);
        }

        public int getVisitas() {
            return visitas;
        }
    }


}
