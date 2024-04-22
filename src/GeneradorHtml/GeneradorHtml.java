package GeneradorHtml;

import Atributos.Atributos;
import Ejecucion.Accion;
import GenerarArchivoTxt.GenerarArchivoTxt;
import Parametros.Parametros;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static Ejecucion.Ejecucion.direccionesFinales;

import static EjecucionHtml.EjecucionHtml.direccionesFinales2;
import static ServidorP.ServidorP.message;

public class GeneradorHtml {

    public void Generar(String tipo, Atributos atributos, Parametros parametros){
        String filePath = "src/DIrectorio/pagina/"+parametros.getPagina()+".html";

        String htmlContent = "";

        if (Accion.NUEVO_SITIO_WEB.toString().equals(tipo)){
            System.out.println("Generando nuevo sitio");
            htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>Mi Página Web</title>\n" +
                    "<script>"+
                    "window.onload = function() {"+
                    "var xhr = new XMLHttpRequest();"+
                    "xhr.open(\"GET\", \"http://localhost:5000/\", true);"+
                    "xhr.onreadystatechange = function() {"+
                    " if (xhr.readyState === 4 && xhr.status === 200) {"+
                    "console.log(\"Visita registrada correctamente.\");"+
                    "}"+
                    " };"+
                    "xhr.send();"+
                    "};"+
                    "</script>"+
                    "</head>\n" +
                    "<body>\n" +
                    "</body>\n" +
                    "</html>";
        } else if (Accion.NUEVA_PAGINA.toString().equals(tipo)) {
            filePath = "src/DIrectorio/pagina/"+parametros.getId()+".html";
            System.out.println("Generando nueva pagina");
            htmlContent = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<title>"+parametros.getSitio()+"</title>\n" +
                    "<script>"+
                    "window.onload = function() {"+
                    "var xhr = new XMLHttpRequest();"+
                    "xhr.open(\"GET\", \"http://localhost:5000/\", true);"+
                    "xhr.onreadystatechange = function() {"+
                    " if (xhr.readyState === 4 && xhr.status === 200) {"+
                    "console.log(\"Visita registrada correctamente.\");"+
                    "}"+
                    " };"+
                    "xhr.send();"+
                    "};"+
                    "</script>"+
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>"+
                    parametros.getTitulo() +
                    "</h1>"+
                    "</body>\n" +
                    "</html>";
        } else if (Accion.AGREGAR_COMPONENTE.toString().equals(tipo)) {
            if (parametros.getClase().equals("TITULO")) {
                htmlContent = "<h1 style = \" " +
                        "text-align: " + atributos.getAlineacion() + "; " +
                        "color: " + atributos.getColor() + "; " +
                        "\">\n" +
                        atributos.getTexto() +
                        "</h1>";
                try {
                    String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
                    htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

                } catch (IOException e) {
                    System.err.println("Error al leer el archivo HTML: " + e.getMessage());
                }
            }
            if (parametros.getClase().equals("PARRAFO")) {
                htmlContent = "<p style = \" " +
                        "color: " + atributos.getColor() + "; " +
                        "text-align: " + atributos.getAlineacion() + "; " +
                        "color: " + atributos.getColor() + "; " +
                        "\">\n" +
                        atributos.getTexto() +
                        "</p>";
                try {
                    String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
                    htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

                } catch (IOException e) {
                    System.err.println("Error al leer el archivo HTML: " + e.getMessage());
                }
            }
            if (parametros.getClase().equals("IMAGEN")) {
                htmlContent = "<div style=\"display: flex; justify-content:" + atributos.getAlineacion() + ";\">" +
                        "<img src = \""+atributos.getOrigen() + "\""+
                        "width = \"" +atributos.getAncho()  + "\""+
                        "heigth = \"" +atributos.getAltura()+"\">" +
                        "</div>\n";
                try {
                    String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
                    htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

                } catch (IOException e) {
                    System.err.println("Error al leer el archivo HTML: " + e.getMessage());
                }
            }
            if (parametros.getClase().equals("VIDEO")) {
                htmlContent = "<center><video controls " +
                        "width=\"" + atributos.getAncho() +
                        "\" height=\"" + atributos.getAltura() + "\">" +
                        "<source src=\"" + atributos.getOrigen() + "\" type=\"video/mp4\">" +
                        "</video></center>";
                try {
                    String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
                    htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

                } catch (IOException e) {
                    System.err.println("Error al leer el archivo HTML: " + e.getMessage());
                }
            }
            if (parametros.getClase().equals("MENU")) {
                StringBuilder listas = new StringBuilder();
                System.out.println("Valor Menus : " + direccionesFinales.toString());
                if (direccionesFinales.isEmpty()){
                    direccionesFinales = direccionesFinales2;
                }
                for (String direccionesFinale : direccionesFinales) {
                    listas.append("<li>\n" + "<a style=\"color: wheat;\" href=\"").append(direccionesFinale).append(".html").append("\">").append(direccionesFinale).append("</a>").append("</li>\n");
                }

                System.out.println("Listasssssssssss ****************  :  "+ listas);
                htmlContent = "<ul  style=\"\n" +
                        "    list-style-type: none;\n" +
                        "    display: flex;\n" +
                        "    flex-direction: row;\n" +
                        "    margin: 0;\n" +
                        "    padding: 10px;\n" +
                        "    background-color: #34495e;\n" +
                        "    justify-content: space-evenly;\n" +
                        "    color: wheat;\n" +
                        "\">\n" +
                        "    <li><a style=\"color: wheat;\" href=\"index.html\">Home</a></li>\n" +
                            listas+
                        "</ul>\n";


                try {
                    String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
                    htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

                } catch (IOException e) {
                    System.err.println("Error al leer el archivo HTML: " + e.getMessage());
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
            System.out.println("El archivo HTML se ha generado/actualizado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al generar el archivo HTML: " + e.getMessage());
        }
    }

    private static void agregarComponente(String htmlContent, String filePath) {
        try {
            String contenidoExistente = new String(Files.readAllBytes(Paths.get(filePath)));
            htmlContent = contenidoExistente.replace("</body>", htmlContent + "\n</body>");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo HTML: " + e.getMessage());
        }
    }
}
