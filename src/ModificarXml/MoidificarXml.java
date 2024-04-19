package ModificarXml;

import Parametros.Parametros;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoidificarXml {
    public MoidificarXml() {
    }

    public static Document accionInicial() {
        Document doc = null;
        try {
            File archivoXML = new File("/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/pagina/xml.xml");
            if (!archivoXML.exists()) {
                // Si el archivo no existe, puedes crear un nuevo documento XML en su lugar
                System.out.println("El archivo XML no existe en la ubicación especificada.");
                // Aquí puedes crear un nuevo documento XML si es necesario
                // Por ejemplo, doc = crearNuevoDocumento();
            } else {
                System.out.println(archivoXML);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(archivoXML);
                doc.getDocumentElement().normalize();
            }
        } catch (Exception e) {
            // Maneja los errores de manera apropiada
            e.printStackTrace();
        }
        return doc;
    }


    public static void modificarAccion(Document doc, String id, String nombreAtributo, String nuevoValor) {
        NodeList acciones = doc.getElementsByTagName("accion");
        for (int i = 0; i < acciones.getLength(); i++) {
            Node accion = acciones.item(i);
            if (accion.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoAccion = (Element) accion;
                // Obtener el valor del ID de la acción
                String valorId = elementoAccion.getElementsByTagName("parametro").item(0).getTextContent().trim().replaceAll("[\\[|\\]]", "");
                System.out.println(valorId);

                if (valorId.equals(id)) {
                    // La acción con el ID especificado fue encontrada
                    NodeList atributos = elementoAccion.getElementsByTagName("atributo");
                    for (int j = 0; j < atributos.getLength(); j++) {
                        Element atributo = (Element) atributos.item(j);
                        if (atributo.getAttribute("nombre").equals(nombreAtributo)) {
                            // Modificar el atributo con el nuevo valor
                            atributo.setTextContent("[" + nuevoValor + "]");
                            guardarCambios(doc);
                            return;
                        }
                    }
                    // Si llegamos aquí, el atributo especificado no se encontró dentro de la acción
                    System.out.println("El atributo especificado no se encontró dentro de la acción con el ID proporcionado.");
                    return;
                }
            }
        }
        // Si llegamos aquí, significa que no se encontró ninguna acción con el ID especificado
        System.out.println("No se encontró ninguna acción con el ID especificado.");
    }



    public static void eliminarAccion(Document doc, String nombreAccion) {
        NodeList acciones = doc.getElementsByTagName("accion");
        for (int i = 0; i < acciones.getLength(); i++) {
            Node accion = acciones.item(i);
            if (accion.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoAccion = (Element) accion;
                if (elementoAccion.getAttribute("nombre").equals(nombreAccion)) {
                    accion.getParentNode().removeChild(accion);
                    // Guardar los cambios en el archivo
                    guardarCambios(doc);
                    System.out.println("La acción '" + nombreAccion + "' ha sido eliminada del archivo XML.");
                    return; // Terminar después de eliminar la acción
                }
            }
        }
        System.out.println("La acción '" + nombreAccion + "' no se encontró en el archivo XML.");
    }

    public static void eliminarAccionPorId(Document doc, String idAccion) {
        NodeList parametros = doc.getElementsByTagName("parametro");
        for (int i = 0; i < parametros.getLength(); i++) {
            Node parametro = parametros.item(i);
            if (parametro.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoParametro = (Element) parametro;
                // Verificar si el nombre del parametro es "ID"
                if (elementoParametro.getAttribute("nombre").equals("ID")) {
                    // Obtener el valor del ID
                    String id = elementoParametro.getTextContent().trim(); // Obtener el contenido de texto del parametro
                    // Verificar si el ID coincide con el ID buscado
                    if (id.equals("[" + idAccion + "]")) {
                        // Eliminar la accion
                        parametro.getParentNode().getParentNode().getParentNode().removeChild(parametro.getParentNode().getParentNode());
                        // Guardar los cambios en el archivo
                        guardarCambios(doc);
                        System.out.println("La acción con ID '" + idAccion + "' ha sido eliminada del archivo XML.");
                        return; // Terminar después de eliminar la acción
                    }
                }
            }
        }
        System.out.println("No se encontró ninguna acción con ID '" + idAccion + "' en el archivo XML.");
    }
    public static void eliminarAccionPorPagina(Document doc, String pagina) {
        NodeList parametros = doc.getElementsByTagName("parametro");
        for (int i = 0; i < parametros.getLength(); i++) {
            Node parametro = parametros.item(i);
            if (parametro.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoParametro = (Element) parametro;
                // Verificar si el nombre del parametro es "ppagina"
                if (elementoParametro.getAttribute("nombre").equals("PAGINA")) {
                    // Obtener el valor de ppagina
                    String valorPagina = elementoParametro.getTextContent().trim();
                    // Verificar si el valor de ppagina coincide con la página buscada
                    if (valorPagina.equals("[" + pagina + "]")) {
                        // Eliminar la acción
                        parametro.getParentNode().getParentNode().getParentNode().removeChild(parametro.getParentNode().getParentNode());
                        // Guardar los cambios en el archivo
                        guardarCambios(doc);
                        System.out.println("La acción con ppagina '" + pagina + "' ha sido eliminada del archivo XML.");
                        return; // Terminar después de eliminar la acción
                    }
                }
            }
        }
        System.out.println("No se encontró ninguna acción con ppagina '" + pagina + "' en el archivo XML.");
    }


    public static void anadirAccion(Document doc, List<Element> nuevasAcciones) {
        System.out.println("Entrando a anadir accion");
        Element accionesElement = (Element) doc.getElementsByTagName("acciones").item(0);
        if (accionesElement == null) {
            System.out.println("Entrando a anadir accion2");
            accionesElement = doc.createElement("acciones");
            doc.getDocumentElement().appendChild(accionesElement);
        }
        System.out.println("Entrando a anadir accion3");
        try{
            for (Element nuevaAccion : nuevasAcciones) {
                // Clonar el nodo de la nueva acción para que pertenezca al mismo documento
                Node nuevaAccionClonada = doc.importNode(nuevaAccion, true);
                accionesElement.appendChild(nuevaAccionClonada);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        System.out.println("Entrando a anadir accion4");
        // Guardar los cambios en el archivo
        guardarCambios(doc);
        System.out.println("Se han añadido las acciones al archivo XML.");
    }

    public static void anadirPagina(Document doc, Parametros parametros) {
        // Crear el elemento de la página
        Element paginaElement = doc.createElement("accion");
        paginaElement.setAttribute("nombre", "NUEVA_PAGINA");

        // Crear el elemento de los parámetros
        Element parametrosElement = doc.createElement("parametros");

        // Agregar los parámetros al elemento de los parámetros
        agregarParametro(doc, parametrosElement, "ID", "["+parametros.getId()+"]");
        agregarParametro(doc, parametrosElement, "TITULO", "["+parametros.getTitulo()+"]");
        agregarParametro(doc, parametrosElement, "SITIO", "["+parametros.getSitio()+"]");
        agregarParametro(doc, parametrosElement, "PADRE", "["+parametros.getPadre()+"]");
        agregarParametro(doc, parametrosElement, "USUARIO_CREACION", "["+parametros.getUsuario()+"]");
        agregarParametro(doc, parametrosElement, "FECHA_CREACION", "["+parametros.getFechaCrea()+"]");
        agregarParametro(doc, parametrosElement, "FECHA_MODIFICACION", "["+parametros.getFechaModi()+"]");
        agregarParametro(doc, parametrosElement, "USUARIO_MODIFICACION", "["+parametros.getUsuarioMod()+"]");

        // Añadir los parámetros al elemento de la página
        paginaElement.appendChild(parametrosElement);

        // Añadir la nueva página al documento XML
        MoidificarXml.anadirAccionPagina(doc, paginaElement);
    }

    public static void agregarParametro(Document doc, Element accionElement, String nombre, String valor) {
        Element parametroElement = doc.createElement("parametro");
        parametroElement.setAttribute("nombre", nombre);
        parametroElement.setTextContent(valor);
        accionElement.appendChild(parametroElement);
    }

    public static void anadirAccionPagina(Document doc, Element nuevaAccion) {
        Element accionesElement = (Element) doc.getElementsByTagName("acciones").item(0);
        if (accionesElement == null) {
            accionesElement = doc.createElement("acciones");
            doc.getDocumentElement().appendChild(accionesElement);
        }

        Node nuevaAccionClonada = doc.importNode(nuevaAccion, true);
        accionesElement.appendChild(nuevaAccionClonada);

        guardarCambios(doc);
        System.out.println("Se ha añadido la acción al archivo XML.");
    }

    public static void guardarCambios(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/home/carlosl/IdeaProjects/ManejadorDeContenido/src/DIrectorio/pagina/xml.xml"));
            transformer.transform(source, result);
            System.out.println("Los cambios se han guardado correctamente en el archivo XML.");
        } catch (TransformerException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static List<String> obtenerIds(String xmlString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(is);

            NodeList acciones = document.getElementsByTagName("accion");
            List<String> idList = new ArrayList<>();

            for (int i = 0; i < acciones.getLength(); i++) {
                Element accion = (Element) acciones.item(i);
                NodeList parametros = accion.getElementsByTagName("parametro");
                for (int j = 0; j < parametros.getLength(); j++) {
                    Element parametro = (Element) parametros.item(j);
                    if ("ID".equals(parametro.getAttribute("nombre"))) {
                        String id = parametro.getTextContent().replaceAll("[\\[|\\]]", ""); // Eliminar corchetes
                        if (idList.contains(id)) {
                            System.out.println("ID repetido: " + id);
                            return null;
                        } else {
                            idList.add(id);
                        }
                        break;
                    }
                }
            }

            return idList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
