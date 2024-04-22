package Ejecucion;

import Atributos.Atributos;
import DesplegarPagina.DesplegarPagina;
import EjecucionHtml.EjecucionHtml;
import GenerarCarpetas.GenerarCarpeta;
import GeneradorHtml.GeneradorHtml;
import Modelos.Pagina;
import Modelos.Sitio;
import Parametros.Parametros;
import ModificarXml.MoidificarXml;
import VarlorClaseModificador.ValorClaseModificador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import Atributos.Atributo;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static EjecucionHtml.EjecucionHtml.removeXMLDeclaration;
import static ServidorP.ServidorP.sendErrorMessage;

public class Ejecucion {
    private static final DesplegarPagina desplegarPagina = new DesplegarPagina();
    private static final GenerarCarpeta generarCarpeta = new GenerarCarpeta();
    private static final GeneradorHtml generadorHtml = new GeneradorHtml();
    private static List<String> arrayList = new ArrayList<>();
    private static List<String> arrayListSitios = new ArrayList<>();
    private static List<Sitio> arraySitios = new ArrayList<>();
    private static List<Pagina> arrayPaginas = new ArrayList<>();
    public static List<String> direccionesFinales = new ArrayList<>();
    public static int contar ;
    public Ejecucion() {
    }


    public static boolean valor(String valor, Atributos atributos, Parametros parametros){

        System.out.println("VALOR : " + valor);
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
                    direccionesFinales.clear();
                    System.out.println("Direcciones finales antes: " + direccionesFinales);
                    System.out.println("Partes obtenidas:");
                    for (String parte : partes) {
                        System.out.println(parte);
                        for (int i = 0; i < Objects.requireNonNull(arrayListSitios).size(); i++){
                            System.out.println("Repetir 1 "+arrayListSitios.size());
                            for (int j = 0; j < Objects.requireNonNull(arrayPaginas).size(); j++) {
                                if (arraySitios.get(i).getPaginas().get(j).getEtiquetas().contains(parte)){
                                    System.out.println("Repetir"+arrayPaginas.size());
                                    if (contador != j){
                                        direccionesFinales.add(arraySitios.get(i).getPaginas().get(j).getPagina());
                                    }
                                    contador = j;
                                }
                            }
                        }
                    }
                }
                System.out.println("Direcciones finales: " + direccionesFinales);
                System.out.println("Valores ************* " + Arrays.toString(arraySitios.toArray()));
            }
        }

        if (Accion.MODIFICAR_COMPONENTE.toString().equals(valor)){

            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()){
                    System.out.println(parametros);
                    ValorClaseModificador.valorClase(atributos, parametros);
                }
            }
            return true ;
        }
        if (Accion.MODIFICAR_PAGINA.toString().equals(valor)){
            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            System.out.println(atributos);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !atributos.getEtiqueta().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    for (int i = 0; i < atributos.getEtiqueta().size(); i++) {
                        MoidificarXml.modificarEtiquetas(doc, parametros.getId(), atributos.getEtiqueta().get(i));
                    }
                    parametros.setPagina(parametros.getId());
                    contar = 0;
                }
                if ( !parametros.getId().isEmpty() && !parametros.getTitulo().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.modificarTitulo(doc, parametros.getId(),parametros.getTitulo());
                    parametros.setPagina(parametros.getId());
                }
            }
            return true ;
        }
        else if (Accion.BORRAR_COMPONENTE.toString().equals(valor)){
            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !parametros.getPagina().isEmpty() && atributos.getEtiqueta().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.eliminarAccionPorId(doc, parametros.getId());
                }
            }
            return true ;
        }
        else if (Accion.BORRAR_SITIO_WEB.toString().equals(valor)){
            File archivo = new File(rutaArchivo);
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && !parametros.getPagina().isEmpty() && atributos.getEtiqueta().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.eliminarAccionPorId(doc, parametros.getId());
                }
            }
            return true ;
        }
        else if (Accion.BORRAR_PAGINA.toString().equals(valor)){
            File archivo = new File("src/DIrectorio/pagina/"+parametros.getId()+".html");
            System.out.println(parametros);
            if (archivo.exists()) {
                if (!parametros.getId().isEmpty() && atributos.getEtiqueta().isEmpty()){
                    System.out.println(parametros);
                    Document doc = MoidificarXml.accionInicial();
                    MoidificarXml.eliminarAccionPorId(doc, parametros.getId());
                    MoidificarXml.eliminarAccionPorPagina(doc, parametros.getId());
                    if (archivo.delete()) {
                        System.out.println("El archivo fue eliminado exitosamente.");
                        try {
                            sendErrorMessage("Se elimino la pagina con exito");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("No se pudo eliminar el archivo.");
                    }
                }
            }
            return true ;
        }
        else {
            try{
            String xml = EjecucionHtml.convertXMLToString(rutaArchivo);
            assert xml != null;
            String xmlString = removeXMLDeclaration(xml);
            arrayList = MoidificarXml.obtenerIds(xmlString);
            System.out.println(arrayList);
            assert arrayList != null;
            if (!arrayList.contains(parametros.getId())) {
                System.out.println("Valores ************* " + Arrays.toString(arrayList.toArray()));

                    if (!arrayList.contains(parametros.getId())) {
                        arrayList.add(parametros.getId());
                        System.out.println("Valores ************* " + Arrays.toString(arrayList.toArray()));
                    } else {
                        System.out.println("El elemento ya está presente en el ArrayList.");
                        try {
                            sendErrorMessage("El valor ya esta siendo utilizado en algun id");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return false;
                    }
                System.out.println("Nueva pagina inicio");

            } else {
                System.out.println("El elemento ya está presente en el ArrayList.");
                try {
                    sendErrorMessage("El id ya esta siendo utilizado");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }}catch (Exception e){
                System.out.println("El archivo no existe");
            }
            System.out.println("Nueva pagina inicio");
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = formato.format(fechaActual);

            if (Accion.NUEVO_SITIO_WEB.toString().equals(valor)) {
                if (!parametros.getId().isEmpty() && atributos.getEtiqueta().isEmpty()) {
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
                return true;
            }

            System.out.println("Nueva pagina inicio");

            if (Accion.NUEVA_PAGINA.toString().equals(valor)) {
                if (!parametros.getId().isEmpty() && !atributos.getEtiqueta().isEmpty() ) {
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
                    System.out.println("Nueva Pagina Aqui");
                    if (archivo.exists()) {
                        MoidificarXml.anadirPagina(doc, parametros,atributos);
                    }
                }
            }


            if (Accion.AGREGAR_COMPONENTE.toString().equals(valor)) {
                File archivo = new File(rutaArchivo);
                Document doc = MoidificarXml.accionInicial();
                if (archivo.exists() && atributos.getEtiqueta().isEmpty() && !parametros.getId().isEmpty() && !parametros.getClase().isEmpty() && !parametros.getPagina().isEmpty()) {
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
            agregarAtributo(doc, atributosx, "ETIQUETASX", "["+atributos.getEtiquetas()+"]");
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
