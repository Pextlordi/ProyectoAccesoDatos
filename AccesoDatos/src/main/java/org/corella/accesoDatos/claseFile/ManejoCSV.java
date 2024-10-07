package org.corella.accesoDatos.claseFile;

import org.corella.accesoDatos.entidades.Alumno;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ManejoCSV {
    public void run(String rutaFichero) {
        ArrayList<Alumno> listaAlumnos = importarAlumnos(rutaFichero);
        System.out.println(listaAlumnos);
    }

    private static ArrayList<Alumno> importarAlumnos(String rutaFichero) {
        try {
            BufferedReader lectorLineas = new Lector().lectorLineas(new File(rutaFichero));
            String linea;
            ArrayList<Alumno> listaAlumnos = new ArrayList();
            while ((linea = lectorLineas.readLine()) != null) {
                linea = linea.replaceAll(";", "");
                String [] campos = linea.split(",");
                if (!campos[0].equals("Nombre")) {
                    listaAlumnos.add(new Alumno(campos));
                }
            }
            return listaAlumnos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
