package VarlorClaseModificador;

import Atributos.Atributos;
import ModificarXml.MoidificarXml;
import Parametros.Parametros;
import org.w3c.dom.Document;

public class ValorClaseModificador {

    public ValorClaseModificador() {
    }

    public static void valorClase(Atributos atributos, Parametros parametros){
        Document doc = MoidificarXml.accionInicial();
        if (parametros.getClase().equals("TITULO")){
           if (!atributos.getTexto().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "TEXTO",atributos.getTexto());
           if (!atributos.getAlineacion().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ALINEACION",atributos.getAlineacion());
           if (!atributos.getColor().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "COLOR",atributos.getColor());
        }
        if (parametros.getClase().equals("PARRAFO")){
            if (!atributos.getTexto().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "TEXTO",atributos.getTexto());
            if (!atributos.getAlineacion().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ALINEACION",atributos.getAlineacion());
            if (!atributos.getColor().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "COLOR",atributos.getColor());

        }
        if (parametros.getClase().equals("IMAGEN")){
            System.out.println("Modificar Imagen");
            System.out.println("Origen " + atributos.getOrigen());
            if (!atributos.getOrigen().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ORIGEN",atributos.getOrigen());
            if (!atributos.getAlineacion().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ALINEACION",atributos.getAlineacion());
            if (!atributos.getAltura().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ALTURA",atributos.getAltura());
            if (!atributos.getAncho().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ANCHO",atributos.getAncho());
        }
        if (parametros.getClase().equals("VIDEO")){
            if (!atributos.getOrigen().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ORIGEN",atributos.getOrigen());
            if (!atributos.getAltura().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ALTURA",atributos.getAltura());
            if (!atributos.getAncho().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ANCHO",atributos.getAncho());
        }
        if (parametros.getClase().equals("MENU")){
            if (!atributos.getPadre().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "PADRE",atributos.getPadre());
            if (!atributos.getEtiquetas().isEmpty()) MoidificarXml.modificarAccion(doc, parametros.getId(), "ETIQUETAS", atributos.getEtiquetas());

        }
    }
}
