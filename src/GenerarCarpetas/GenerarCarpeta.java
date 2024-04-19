package GenerarCarpetas;

import java.io.File;

public class GenerarCarpeta {

    public GenerarCarpeta(){

    }

    public void generarCarpeta(String nombreCarpeta){
        File directorio = new File("src/DIrectorio/" + nombreCarpeta);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }
}
