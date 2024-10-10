package org.corella.accesoDatos.applications;

import org.corella.accesoDatos.utilsAccesoFichero.Constants;
import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LeerEscribirObjetos {
    private void escribirFlujo() throws IOException {
        FileOutputStream escritor = new Escritor().escritorBytes(Constants.rutaSalidaBytes);
        escritor.write(67);
        String cadenaEscribir = "Hola";
        escritor.write(cadenaEscribir.getBytes());
        escritor.close();
    }
    private void lectorFlujo(String rutaFichero) throws IOException {
        File fichero = new File(rutaFichero);
        FileInputStream lector = new Lector().lectorFlujo(fichero);
        byte[] bytesLeidos = lector.readAllBytes();
        String cadenaBytesLeidos = new String(bytesLeidos, StandardCharsets.UTF_8);
        System.out.println("La cantidad de bytes almacenados es: " + bytesLeidos.length + "\nSu contenido es: " + Arrays.toString(bytesLeidos) + "\nSu posición en memoria es: " + bytesLeidos.toString() + "\nY su contenido en forma legible es: " +  cadenaBytesLeidos);
        lector.close();
    }

    private void escitorTipo() throws IOException {
        DataOutputStream escritor = new Escritor().escritorTipos(Constants.rutaSalidaTipos);
        escritor.writeBoolean(true);
        escritor.writeLong(100);
        escritor.writeLong(System.nanoTime());
        escritor.close();
    }

    public void run (String ruta) throws IOException {
        //escribirFlujo();
        //lectorFlujo(ruta);
        escitorTipo();
    }
}
