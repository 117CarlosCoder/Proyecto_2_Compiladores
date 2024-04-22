package Modelos;

import java.util.List;

public class Sitio {
    private String sitio;
    private List<Pagina> paginas;

    public Sitio(String sitio, List<Pagina> paginas) {
        this.sitio = sitio;
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return "Sitio{" +
                "sitio='" + sitio + '\'' +
                ", paginas=" + paginas +
                '}';
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public List<Pagina> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Pagina> paginas) {
        this.paginas = paginas;
    }
}
