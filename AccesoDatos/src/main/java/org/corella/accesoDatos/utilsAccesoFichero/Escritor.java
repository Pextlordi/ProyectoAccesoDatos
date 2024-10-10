package org.corella.accesoDatos.utilsAccesoFichero;

import java.io.*;

public class Escritor {
    public BufferedWriter escritorLineas(File rutaFichero) throws IOException {
        return new BufferedWriter(new FileWriter(rutaFichero));
    }

    public FileOutputStream escritorBytes(String rutaFichero) throws IOException {
        return new FileOutputStream(rutaFichero);
    }

    public DataOutputStream escritorTipos(String rutaFichero) throws IOException {
        return new DataOutputStream(escritorBytes(rutaFichero));
    }
}
