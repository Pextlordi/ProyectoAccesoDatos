package org.corella.accesoDatos.applications;

import org.corella.accesoDatos.entities.Alumno;
import org.corella.accesoDatos.utilsAccesoFichero.Constants;
import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

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

    private void escritorTipo() throws IOException {
        DataOutputStream escritor = new Escritor().escritorTipos(Constants.rutaSalidaTipos);
        escritor.writeBoolean(true);
        escritor.writeLong(100);
        escritor.writeLong(System.nanoTime());
        escritor.close();
    }

    private void lectorTipo(String rutaFichero) throws IOException {
        DataInputStream lectorTipos = new Lector().lectorTipos(rutaFichero);
        lectorTipos.readBoolean();
        lectorTipos.readLong();
        lectorTipos.readLong();
        lectorTipos.close();
    }

    public void escribirObjeto(Alumno alumno) throws IOException {
        ObjectOutputStream escritorObjectos = new Escritor().escritorObjetos(Constants.rutaSalidaObjetos);
        escritorObjectos.writeObject(alumno);
        escritorObjectos.close();
    }

    public void leerObjeto(String rutaFichero) throws IOException, ClassNotFoundException {
        ObjectInputStream lectorObjeto = new Lector().lectorObjetos(new File(rutaFichero));
        Alumno alumnoLeido = (Alumno) lectorObjeto.readObject();
        System.out.println(alumnoLeido);
        lectorObjeto.close();
    }

    public void run(String ruta) throws IOException, ClassNotFoundException {
        //escribirFlujo();
        //lectorFlujo(ruta);
        //escritorTipo();
        //lectorTipo(ruta);
        /*Alumno romera = new Alumno("Alejandro", 22, "2DAM", 10);
        escribirObjeto(romera);
         */
        leerObjeto(Constants.rutaSalidaObjetos);
    }
}
