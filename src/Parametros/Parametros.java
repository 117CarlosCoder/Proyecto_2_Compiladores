package Parametros;

import Ejecucion.Accion;

public class Parametros {
    private String id;
    private String pagina;
    private String clase;
    private String sitio;
    private String padre;
    private String titulo;
    private String usuario;
    private String usuarioMod;
    private String fechaCrea;
    private String fechaModi;

    public Parametros(String id, String pagina, String clase, String sitio, String padre, String titulo, String usuario, String usuarioMod, String  fechaCrea, String fechaModi) {
        this.id = id;
        this.pagina = pagina;
        this.clase = clase;
        this.sitio = sitio;
        this.padre = padre;
        this.titulo = titulo;
        this.usuario = usuario;
        this.usuarioMod = usuarioMod;
        this.fechaCrea = fechaCrea;
        this.fechaModi = fechaModi;
    }


    public static void ingresar(String valor, String parametro, Parametros parametros){

        if (Parametro.ID.toString().equals(valor)){
            parametros.setId(parametro);

        }
        if (Parametro.CLASE.toString().equals(valor)){
            parametros.setClase(parametro);

        }
        if (Parametro.PADRE.toString().equals(valor)){
            parametros.setPadre(parametro);

        }
        if (Parametro.PAGINA.toString().equals(valor)){
            parametros.setPagina(parametro);

        }
        if (Parametro.SITIO.toString().equals(valor)){
            parametros.setSitio(parametro);

        }
        if (Parametro.TITULO.toString().equals(valor)){
            parametros.setTitulo(parametro);

        }
        if (Parametro.USUARIO_CREACION.toString().equals(valor)){
            parametros.setUsuario(parametro);

        }
        if (Parametro.USUARIO_MODIFICACION.toString().equals(valor)){
            parametros.setUsuarioMod(parametro);

        }
        if (Parametro.FECHA_CREACION.toString().equals(valor)){
            parametros.setFechaCrea(parametro);

        }
        if (Parametro.FECHA_MODIFICACION.toString().equals(valor)){
            parametros.setFechaModi(parametro);

        }


    }


    @Override
    public String toString() {
        return "Parametros{" +
                "id='" + id + '\'' +
                ", pagina='" + pagina + '\'' +
                ", clase='" + clase + '\'' +
                ", sitio='" + sitio + '\'' +
                ", padre='" + padre + '\'' +
                ", titulo='" + titulo + '\'' +
                ", usuario='" + usuario + '\'' +
                ", usuarioMod='" + usuarioMod + '\'' +
                ", fechaCrea='" + fechaCrea + '\'' +
                ", fechaModi='" + fechaModi + '\'' +
                '}';
    }

    public String getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(String usuarioMod) {
        this.usuarioMod = usuarioMod;
    }

    public String getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(String fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getFechaModi() {
        return fechaModi;
    }

    public void setFechaModi(String fechaModi) {
        this.fechaModi = fechaModi;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPagina() {
        if (pagina.isEmpty()){
            this.pagina = "index";
        }
        return pagina;
    }

    public void setPagina(String pagina) {
        if (pagina.isEmpty()){
            this.pagina = "index";
        }
        else{
            this.pagina = pagina;
        }
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
