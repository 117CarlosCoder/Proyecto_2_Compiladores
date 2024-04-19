package Ejecucion;

import Atributos.Atributos;
import DesplegarPagina.DesplegarPagina;
import EjecucionHtml.EjecucionHtml;
import GenerarCarpetas.GenerarCarpeta;
import GeneradorHtml.GeneradorHtml;
import Parametros.Parametros;
import ModificarXml.MoidificarXml;
import VarlorClaseModificador.ValorClaseModificador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static ServidorP.ServidorP.sendErrorMessage;

public class Ejecucion {
    private static final DesplegarPagina desplegarPagina = new DesplegarPagina();
    private static final GenerarCarpeta generarCarpeta = new GenerarCarpeta();
    private static final GeneradorHtml generadorHtml = new GeneradorHtml();
    private static List<String> arrayList = new ArrayList<>();
    public Ejecucion() {
    }


    public static boolean valor(String valor, Atributos atributos, Parametros parametros){

        System.out.println("VALOR : " + valor);
        String rutaArchivo = "src/DIrectorio/pagina/xml.xml";
        File archivop = new File(rutaArchivo);

        if (Accion.MODIFICAR_COMPONENTE.toString().equals(valor)){
            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()){
                    System.out.println(parametros);
                    ValorClaseModificador.valorClase(atributos, parametros);

                }
            }

        }
        else if (Accion.BORRAR_COMPONENTE.toString().equals(valor)){
            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !parametros.getPagina().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.eliminarAccionPorId(doc, parametros.getId());
                }
            }

        }

        else if (Accion.BORRAR_PAGINA.toString().equals(valor)){
            File archivo = new File("src/DIrectorio/pagina/"+parametros.getId()+".html");
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.eliminarAccionPorId(doc, parametros.getId());
                    MoidificarXml.eliminarAccionPorPagina(doc, parametros.getId());
                    if (archivo.delete()) {
                        System.out.println("El archivo fue eliminado exitosamente.");
                    } else {
                        System.out.println("No se pudo eliminar el archivo.");
                    }
                }
            }

        }else {

            if (archivop.exists()) {

                arrayList = MoidificarXml.obtenerIds(EjecucionHtml.convertXMLToString(rutaArchivo));
            }
            if (!arrayList.contains(parametros.getId())) {
                arrayList.add(parametros.getId());
                System.out.println("Valores ************* " + Arrays.toString(arrayList.toArray()));
            } else {
                System.out.println("El elemento ya está presente en el ArrayList.");
                try {
                    sendErrorMessage("El id ya esta siendo utilizado");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }

            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = formato.format(fechaActual);

            if (Accion.NUEVO_SITIO_WEB.toString().equals(valor)) {
                if (!parametros.getId().isEmpty()) {
                    if (parametros.getUsuario().isEmpty()) {
                        parametros.setUsuario("usuario1");
                    }
                    if (parametros.getFechaCrea().isEmpty()) {
                        parametros.setFechaCrea(fechaFormateada);
                    }
                    if (parametros.getFechaModi().isEmpty()) {
                        parametros.setFechaModi(fechaFormateada);
                    }
                    if (parametros.getUsuarioMod().isEmpty()) {
                        parametros.setUsuario("usuario1");
                    }
                    generarCarpeta.generarCarpeta("pagina");
                    generadorHtml.Generar(valor, atributos, parametros);
                }
            }

            if (Accion.NUEVA_PAGINA.toString().equals(valor)) {
                if (!parametros.getId().isEmpty()) {
                    if (parametros.getUsuario().isEmpty()) {
                        parametros.setUsuario("usuario1");
                    }
                    if (parametros.getFechaCrea().isEmpty()) {
                        parametros.setFechaCrea(fechaFormateada);
                    }
                    if (parametros.getFechaModi().isEmpty()) {
                        parametros.setFechaModi(fechaFormateada);
                    }
                    if (parametros.getUsuarioMod().isEmpty()) {
                        parametros.setUsuario("usuario1");
                    }
                    System.out.println(parametros.getUsuario());
                    generadorHtml.Generar(valor, atributos, parametros);
                    File archivo = new File(rutaArchivo);
                    Document doc = MoidificarXml.accionInicial();
                    if (archivo.exists()) {
                        List<Element> elementos = generadorXml(parametros, atributos);
                        System.out.println(Arrays.toString(elementos.toArray()));
                        MoidificarXml.anadirPagina(doc, parametros);
                    }
                }
            }

            if (Accion.AGREGAR_COMPONENTE.toString().equals(valor)) {
                File archivo = new File(rutaArchivo);
                Document doc = MoidificarXml.accionInicial();
                if (archivo.exists() && !parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()) {
                    List<Element> elementos = generadorXml(parametros, atributos);
                    System.out.println(Arrays.toString(elementos.toArray()));
                    MoidificarXml.anadirAccion(doc, elementos);
                }
                if (!archivo.exists() && !parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()) {
                    generadorHtml.Generar(valor, atributos, parametros);
                }

            }


        }

        return true;
    }

    public static List<Element> generadorXml(Parametros parametros, Atributos atributos){

        List<Element> nuevasAcciones = new ArrayList<>();
        Document doc = MoidificarXml.accionInicial();



        Element nuevaAccion = doc.createElement("accion");
        nuevaAccion.setAttribute("nombre", "AGREGAR_COMPONENTE");
        String nombreAccion = nuevaAccion.getAttribute("nombre");
        System.out.println("Nueva acción creada: nombre=" + nombreAccion);

        Element parametrosx = doc.createElement("parametros");

        agregarParametro(doc, parametrosx, "ID", parametros.getId());
        agregarParametro(doc, parametrosx, "PAGINA", parametros.getPagina());
        agregarParametro(doc, parametrosx, "CLASE", parametros.getClase());

        nuevaAccion.appendChild(parametrosx);

        Element atributosx = doc.createElement("atributos");


        if (!atributos.getAltura().isEmpty()){
            agregarAtributo(doc, atributosx, "ALTURA", "["+atributos.getAltura()+"]");
        }
        if (!atributos.getAncho().isEmpty()){
            agregarAtributo(doc, atributosx, "ANCHO", "["+atributos.getAncho()+"]");
        }
        if (!atributos.getAlineacion().isEmpty()){
            agregarAtributo(doc, atributosx, "ALINEACION", "["+atributos.getAlineacion()+"]");
        }
        if (!atributos.getColor().isEmpty()){
            agregarAtributo(doc, atributosx, "COLOR", "["+atributos.getColor()+"]");
        }
        if (!atributos.getTexto().isEmpty()){
            agregarAtributo(doc, atributosx, "TEXTO", "["+atributos.getTexto()+"]");
        }
        if (!atributos.getOrigen().isEmpty()){
            agregarAtributo(doc, atributosx, "ORIGEN", "["+atributos.getOrigen()+"]");
        }
        if (!atributos.getPadre().isEmpty()){
            agregarAtributo(doc, atributosx, "PADRE", "["+atributos.getPadre()+"]");
        }
        if (!atributos.getEtiquetas().isEmpty()){
            agregarAtributo(doc, atributosx, "ETIQUETAS", "["+atributos.getEtiquetas()+"]");
        }
        nuevaAccion.appendChild(atributosx);
        nuevasAcciones.add(nuevaAccion);

        System.out.println("Acciones agregadas:");
        for (Element accion : nuevasAcciones) {
            System.out.println("Nombre de la acción: " + accion.getAttribute("nombre"));

            NodeList parametrosList = accion.getElementsByTagName("parametros");
            if (parametrosList.getLength() > 0) {
                Element parametrosxs = (Element) parametrosList.item(0);

                NodeList atributosList = parametrosxs.getElementsByTagName("atributos");
                if (atributosList.getLength() > 0) {
                    Element atributosxs = (Element) atributosList.item(0);
                    NodeList atributoList = atributosxs.getChildNodes();
                    for (int i = 0; i < atributoList.getLength(); i++) {
                        if (atributoList.item(i) instanceof Element) {
                            Element atributo = (Element) atributoList.item(i);
                            System.out.println(atributo.getTagName() + ": " + atributo.getTextContent());
                        }
                    }
                }
            }
        }

        return nuevasAcciones;
    }

    private static void agregarParametro(Document doc, Element parametrosx, String nombre, String valor) {
        Element parametro = doc.createElement("parametro");
        parametro.setAttribute("nombre", nombre);
        parametro.appendChild(doc.createTextNode("[" + valor + "]"));
        parametrosx.appendChild(parametro);
    }

    private static void agregarAtributo(Document doc, Element atributosx, String nombre, String valor) {
        Element atributo = doc.createElement("atributo");
        atributo.setAttribute("nombre", nombre);
        atributo.appendChild(doc.createTextNode(valor));
        atributosx.appendChild(atributo);
    }

}
