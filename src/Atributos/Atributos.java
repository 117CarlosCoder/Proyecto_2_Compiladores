package Atributos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Atributos {

    private String texto;
    private String alineacion;
    private String color;
    private String origen;
    private String altura;
    private String ancho;
    private String padre;
    private String etiquetas;
    private final List<String> etiqueta = new ArrayList<>();

    public Atributos(String texto, String alineacion, String color, String origen, String altura, String ancho, String padre, String etiquetas) {
        this.texto = texto;
        this.alineacion = alineacion;
        this.color = color;
        this.origen = origen;
        this.altura = altura;
        this.ancho = ancho;
        this.padre = padre;
        this.etiquetas = etiquetas;
    }

    public static void ingresar(String valor, String parametro, Atributos atributos){
        System.out.println("Valor ingresado en atributos " + valor + " " + parametro);

        if (Atributo.ALTURA.toString().equals(valor)){
            atributos.setAltura(parametro);

        }
        if (Atributo.ANCHO.toString().equals(valor)){
            atributos.setAncho(parametro);

        }
        if (Atributo.PADRE.toString().equals(valor)){
            atributos.setPadre(parametro);

        }
        if (Atributo.COLOR.toString().equals(valor)){
            atributos.setColor(parametro);

        }
        if (Atributo.ALINEACION.toString().equals(valor)){
            System.out.println("Entrnado a Alineacion " + valor + " " + parametro);
            atributos.setAlineacion(parametro);

        }
        if (Atributo.ETIQUETA.toString().equals(valor)){
            atributos.getEtiqueta().add(parametro);
            System.out.println(atributos);
        }
        if (Atributo.ETIQUETASX.toString().equals(valor)){
            atributos.setEtiquetas(parametro);

        }
        if (Atributo.ORIGEN.toString().equals(valor)){
            atributos.setOrigen(parametro);

        }
        if (Atributo.TEXTO.toString().equals(valor)){
            atributos.setTexto(parametro);

        }
    }

    @Override
    public String toString() {
        return "Atributos{" +
                "texto='" + texto + '\'' +
                ", alineacion='" + alineacion + '\'' +
                ", color='" + color + '\'' +
                ", origen='" + origen + '\'' +
                ", altura='" + altura + '\'' +
                ", ancho='" + ancho + '\'' +
                ", padre='" + padre + '\'' +
                ", etiquetas='" + etiquetas + '\'' +
                ", etiqueta=" + etiqueta +
                '}';
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(String alineacion) {
        System.out.println("Valor de alinear " + alineacion);
            if (alineacion.equals("CENTRAR") || alineacion.equals("center")){
                this.alineacion = "center";
            }
            if (alineacion.equals("IZQUIERDA") || alineacion.equals("left")){
                this.alineacion = "left";
            }
            if (alineacion.equals("DERECHA") || alineacion.equals("right")){
                this.alineacion = "right";
            }
            if (alineacion.equals("JUSTIFICAR") || alineacion.equals("justify")){
                this.alineacion = "justify";
            }
    }

    public List<String> getEtiqueta() {
        return etiqueta;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }
}
