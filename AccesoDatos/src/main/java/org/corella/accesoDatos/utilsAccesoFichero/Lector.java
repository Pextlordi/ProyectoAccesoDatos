package org.corella.accesoDatos.utilsAccesoFichero;

import java.io.*;

public class Lector {
    public FileReader lectorCaracteres (File rutaFichero) throws FileNotFoundException {
        return new FileReader(rutaFichero);
    }
    public BufferedReader lectorLineas (File rutaFichero) throws FileNotFoundException {
        return new BufferedReader(new FileReader(rutaFichero));
    }
}
