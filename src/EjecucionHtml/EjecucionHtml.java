package EjecucionHtml;

import Atributos.Atributos;
import DesplegarPagina.DesplegarPagina;
import Ejecucion.Accion;
import EnvioMensaje.EnvioMensaje;
import GeneradorHtml.GeneradorHtml;
import GenerarArchivoTxt.GenerarArchivoTxt;
import JcupHtml.parser;
import Jflex.Lexico;
import Parametros.Parametros;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import static Conexion.Conexion.cs;
import static ServidorP.ServidorP.message;
import static ServidorP.ServidorP.sendErrorMessage;

public class EjecucionHtml {
    private static final DesplegarPagina desplegarPagina = new DesplegarPagina();
    private static final GeneradorHtml generadorHtml = new GeneradorHtml();
    private static String usuarioG;
    public static void valor(String valor, Atributos atributos, Parametros parametros){

        System.out.println("VALOR : " + valor);

        if (Accion.NUEVO_SITIO_WEB.toString().equals(valor)){
            if (!parametros.getId().isEmpty() && !parametros.getFechaCrea().isEmpty() && !parametros.getFechaModi().isEmpty() && !parametros.getUsuarioMod().isEmpty()) {
                generadorHtml.Generar(valor, atributos, parametros);
            }
        }
        if (Accion.NUEVA_PAGINA.toString().equals(valor)){
            if (!parametros.getId().isEmpty() && !parametros.getFechaCrea().isEmpty() && !parametros.getFechaModi().isEmpty() && !parametros.getUsuarioMod().isEmpty()) {
                generadorHtml.Generar(valor, atributos, parametros);
            }
        }

        if (Accion.AGREGAR_COMPONENTE.toString().equals(valor)){
            if (!parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()) {
                generadorHtml.Generar(valor, atributos, parametros);
            }
        }

    }

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

    public static String removeXMLDeclaration(String xmlString) {
        // Encuentra la posición del primer '>'
        int index = xmlString.indexOf(">");

        // Si se encuentra el símbolo '>', elimina la parte de la cadena hasta la siguiente posición
        if (index != -1) {
            return xmlString.substring(index + 1);
        } else {
            // Si no se encuentra el símbolo '>', devuelve la cadena original
            return xmlString;
        }
    }

    public static String convertXMLToString(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(filePath));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            javax.xml.transform.TransformerFactory.newInstance().newTransformer().transform(new javax.xml.transform.dom.DOMSource(document), new javax.xml.transform.stream.StreamResult(outputStream));

            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void ejecutarHtml(String mensaje,String pagina, boolean valor){
        String usuario = "pagina";
        String filePath = "/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/"+usuario+"/xml.xml";
        String filePath2 = "/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/"+usuario+"/"+pagina+".html";// Reemplaza con la ruta de tu archivo XML
        File archivo = new File(filePath);
        File archivo2 = new File(filePath2);
        usuarioG = pagina;
        System.out.println("Valor pagina a entrar " + pagina);

        try {
            sendErrorMessage(mensaje);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!valor){return;}
        //Thread envioThread = new Thread(new EnvioMensaje(cs,mensaje));
        //envioThread.start();
        if (archivo.exists()) {
            String xmlString = convertXMLToString(filePath);
            assert xmlString != null;
            xmlString = removeXMLDeclaration(xmlString);
            interpretar(xmlString);
            System.out.println(xmlString);

        }
        else if (archivo2.exists()){
            ejecucion();
        }
    }

    public static void ejecucion(){
        GenerarArchivoTxt generarArchivoTxt = new GenerarArchivoTxt();
        generarArchivoTxt.generarArchivoTxt(message,"pagina", "xml");
        try {
            desplegarPagina.detenerServidor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Thread serverThread = new Thread(() -> {
            desplegarPagina.despliegue(usuarioG);
        });
        serverThread.start();
    }
}
