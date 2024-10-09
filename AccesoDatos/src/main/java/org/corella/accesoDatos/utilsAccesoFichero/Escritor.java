package org.corella.accesoDatos.utilsAccesoFichero;

import java.io.*;

public class Escritor {
    public BufferedWriter escritorLineas (File rutaFichero) throws IOException {
        return new BufferedWriter(new FileWriter(rutaFichero));
    }
}
