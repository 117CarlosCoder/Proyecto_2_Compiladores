package Modelos;

import java.util.List;

public class Pagina {
    private String pagina;
    private List<String> etiquetas;

    public Pagina(String pagina, List<String> etiquetas) {
        this.pagina = pagina;
        this.etiquetas = etiquetas;
    }

    @Override
    public String toString() {
        return "Pagiina{" +
                "pagina='" + pagina + '\'' +
                ", etiquetas=" + etiquetas +
                '}';
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }
}
