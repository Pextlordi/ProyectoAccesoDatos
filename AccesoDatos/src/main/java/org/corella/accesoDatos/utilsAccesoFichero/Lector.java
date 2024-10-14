package org.corella.accesoDatos.utilsAccesoFichero;

import java.io.*;

public class Lector {
    public FileReader lectorCaracteres(File rutaFichero) throws FileNotFoundException {
        return new FileReader(rutaFichero);
    }

    public BufferedReader lectorLineas(File rutaFichero) throws FileNotFoundException {
        return new BufferedReader(new FileReader(rutaFichero));
    }

    public FileInputStream lectorFlujo(File rutaFichero) throws FileNotFoundException {
        return new FileInputStream(rutaFichero);
    }

    public DataInputStream lectorTipos(String rutaFichero) throws FileNotFoundException {
        return new DataInputStream(lectorFlujo(new File(rutaFichero)));
    }

    public ObjectInputStream lectorObjetos(File rutaFichero) throws IOException {
        return new ObjectInputStream(lectorFlujo(rutaFichero));
    }
}
