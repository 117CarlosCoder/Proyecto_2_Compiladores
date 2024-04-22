package EjecucionHtml;

import Atributos.Atributos;
import DesplegarPagina.DesplegarPagina;
import Ejecucion.Accion;
import GeneradorHtml.GeneradorHtml;
import GenerarArchivoTxt.GenerarArchivoTxt;
import JcupHtml.parser;
import Jflex.Lexico;
import Modelos.Pagina;
import Modelos.Sitio;
import ModificarXml.MoidificarXml;
import Parametros.Parametros;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ServidorP.ServidorP.message;
import static ServidorP.ServidorP.sendErrorMessage;

public class EjecucionHtml {
    private static final DesplegarPagina desplegarPagina = new DesplegarPagina();
    private static final GeneradorHtml generadorHtml = new GeneradorHtml();
    public static String usuarioG;
    private static List<String> arrayList = new ArrayList<>();
    private static List<String> arrayListSitios = new ArrayList<>();
    private static List<Sitio> arraySitios = new ArrayList<>();
    private static List<Pagina> arrayPaginas = new ArrayList<>();
    public static List<String> direccionesFinales2 = new ArrayList<>();
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
            if (!parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty() ) {
                System.out.println(atributos);
                String rutaArchivo = "src/DIrectorio/pagina/xml.xml";
                File archivop = new File(rutaArchivo);
                if (archivop.exists() && "MENU".equals(parametros.getClase())){
                    System.out.println("Parametros antes iniciar direcciones " + parametros);
                    if (archivop.exists() && "MENU".equals(parametros.getClase())) {
                        System.out.println("Iniciando direcciones");
                        String xml = EjecucionHtml.convertXMLToString(rutaArchivo);
                        assert xml != null;
                        String xmlString = removeXMLDeclaration(xml);
                        arrayList = MoidificarXml.obtenerIds(xmlString);
                        arrayListSitios = MoidificarXml.obtenerIdsSitios(xmlString);
                        List<String> arrayListPaginas;
                        arrayPaginas.clear();
                        if (!atributos.getEtiquetas().isEmpty()) {
                            for (int i = 0; i < Objects.requireNonNull(arrayListSitios).size(); i++) {


                                arrayListPaginas = MoidificarXml.obtenerIdsNuevasPaginasConPadre(xml, arrayListSitios.get(i));
                                System.out.println("Buscando en sitio id "+arrayListSitios.get(i));
                                System.out.println(arrayListPaginas);
                                for (int j = 0; j < Objects.requireNonNull(arrayListPaginas).size(); j++) {
                                    List<String> arrayListEtiquetas;
                                    arrayListEtiquetas = MoidificarXml.obtenerEtiquetas(xml, arrayListPaginas.get(j));
                                    arrayPaginas.add(new Pagina(arrayListPaginas.get(j), arrayListEtiquetas));
                                }
                                arraySitios.add(new Sitio(arrayListSitios.get(i), arrayPaginas));
                            }
                            String[] partes = atributos.getEtiquetas().split("\\|");
                            int contador = -1;
                            direccionesFinales2.clear();
                            System.out.println("Direcciones finales antes: " + direccionesFinales2);
                            System.out.println("Partes obtenidas:");
                            for (String parte : partes) {
                                System.out.println(parte);
                                for (int i = 0; i < Objects.requireNonNull(arrayListSitios).size(); i++){
                                    System.out.println("Repetir 1 "+arrayListSitios.size());
                                    for (int j = 0; j < Objects.requireNonNull(arrayPaginas).size(); j++) {
                                        if (arraySitios.get(i).getPaginas().get(j).getEtiquetas().contains(parte)){
                                            System.out.println("Repetir"+arrayPaginas.size());
                                            if (contador != j){
                                                direccionesFinales2.add(arraySitios.get(i).getPaginas().get(j).getPagina());
                                            }
                                            contador = j;
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("Direcciones finales: " + direccionesFinales2);
                        System.out.println("Valores ************* " + Arrays.toString(arraySitios.toArray()));
                    }
                }

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

    public static void ejecutarHtml(String mensaje,String pagina , boolean valor){
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
