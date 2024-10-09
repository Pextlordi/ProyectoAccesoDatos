package org.corella.accesoDatos.applications;

import org.corella.accesoDatos.entities.Alumno;
import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;
import org.corella.accesoDatos.utilsAccesoFichero.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class ManejoCSV {
    public void run(String rutaFichero) {
        TreeSet<Alumno> listaAlumnos = importarAlumnos(rutaFichero);
        System.out.println(listaAlumnos);
        try {
            /*
            Usando una lista y sin compareTo:
            listaAlumnos.sort(Comparator.comparingInt(Alumno::getEdad).thenComparing(Comparator.comparingDouble(Alumno::getNota).reversed()));
             */
            exportarAlumno(listaAlumnos);
            System.out.println("End");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static TreeSet<Alumno> importarAlumnos(String rutaFichero) {
        try {
            BufferedReader lectorLineas = new Lector().lectorLineas(new File(rutaFichero));
            String linea;
            TreeSet <Alumno> listaAlumnos = new TreeSet<>();
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

    private void exportarAlumno (TreeSet<Alumno> listaAlumnos) throws IOException {
        File rutaFichero = new File(Constants.rutaSalidaFicheroCSV);
        BufferedWriter escritor = new Escritor().escritorLineas(rutaFichero);
        for (Alumno alumno : listaAlumnos) {
            escritor.append(alumno.toStringCSV());
            escritor.newLine();
        }
        escritor.newLine();
        escritor.flush();
        escritor.close();
    }
}
