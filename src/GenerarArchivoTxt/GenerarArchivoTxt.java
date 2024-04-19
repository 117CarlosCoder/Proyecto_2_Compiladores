package GenerarArchivoTxt;

import ModificarXml.MoidificarXml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerarArchivoTxt {

    public GenerarArchivoTxt() {
    }

    public void generarArchivoTxt(String texto, String usuario, String id){
        String rutaArchivo = "src/DIrectorio/"+usuario+"/"+id+".xml";
        FileWriter escritor = null;
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            // El archivo ya existe
            System.out.println("El archivo ya existe.");
        } else {
            try {
                escritor = new FileWriter("src/DIrectorio/"+usuario+"/"+id+".xml");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Escribir el contenido XML en el archivo
            try {
                escritor.write(texto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Cerrar el FileWriter
            try {
                escritor.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
