package org.corella.accesoDatos.applications;

import com.sun.source.tree.Tree;
import org.corella.accesoDatos.entities.Alumno;
import org.corella.accesoDatos.utilsAccesoFichero.Constants;
import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        try {
            Alumno alumnoLeido;
            while ((alumnoLeido = (Alumno) lectorObjeto.readObject()) != null) {
                System.out.println(alumnoLeido);
            }
        } catch (EOFException e) {
            System.out.println("Fin de lectura");
        }
        lectorObjeto.close();
    }

    //EjercicioPractico1

    private void leerTexto(String rutaFichero) throws IOException {
        BufferedReader lectorTexto = new Lector().lectorLineas(new File(rutaFichero));
        String lineaLeida;
        while ((lineaLeida = lectorTexto.readLine()) != null) {
            System.out.println(lineaLeida);
        }
        lectorTexto.close();
    }

    public TreeSet<Alumno> leerClaseAlumnos(String rutaFichero) throws IOException, ClassNotFoundException {
        ObjectInputStream lectorAlumno = new Lector().lectorObjetos(new File(rutaFichero));
        TreeSet <Alumno> claseAlumnos = new TreeSet<>();
        try {
            Alumno alumnoLeido;
            while ((alumnoLeido = (Alumno) lectorAlumno.readObject()) != null) {
                claseAlumnos.add(alumnoLeido);
            }
        } catch (EOFException e) {
            System.out.println("Fin de lectura de los alumnos de la clase");
        }
        lectorAlumno.close();
        return claseAlumnos;
    }

    public void escribirClaseAlumnos() throws IOException {
        ObjectOutputStream escritorAlumnos = new Escritor().escritorObjetos(Constants.rutaSalidaAlumnos);
        TreeSet<Alumno> conjuntosAlumnos = new TreeSet<>();
        conjuntosAlumnos.add(new Alumno("Petteri", "Ketola"));
        conjuntosAlumnos.add(new Alumno("Alejandro", "Romera"));
        conjuntosAlumnos.add(new Alumno("Andreia", "Costán"));
        conjuntosAlumnos.add(new Alumno("David", "Costán"));
        conjuntosAlumnos.add(new Alumno("Nicolas", "Martín"));
        conjuntosAlumnos.add(new Alumno("Unai", "Cuesta"));
        conjuntosAlumnos.add(new Alumno("Alvaro", "del Cerro"));
        conjuntosAlumnos.add(new Alumno("Cristian", "Wilson"));
        conjuntosAlumnos.add(new Alumno("Dikra", "Asghar"));
        conjuntosAlumnos.add(new Alumno("Mohammed", "Boussaid"));
        conjuntosAlumnos.add(new Alumno("Harry", "Bähr"));
        conjuntosAlumnos.add(new Alumno("Marcos", "Krasimirov"));

        //conjuntosAlumnos.add(new Alumno("Alejandro", "Romera"));
        for (Alumno alumnoaEscribir : conjuntosAlumnos) {
            escritorAlumnos.writeObject(alumnoaEscribir);
        }
        escritorAlumnos.close();
    }

    public void escribirResumen() throws IOException, ClassNotFoundException {
        TreeSet<Alumno> conjuntoAlumnos = leerClaseAlumnos(Constants.rutaSalidaAlumnos);
        BufferedWriter escritorTexto = new Escritor().escritorLineas(new File(Constants.rutaSalidaResumen));
        String introduccion = "Los alumnos aprobados de la clase de 2DAM son: ";
        escritorTexto.write(introduccion);
        for (Alumno alumnoLeido : conjuntoAlumnos) {
            if (alumnoLeido.getNota() >= 5) {
                String contenidoAlumno = alumnoLeido.getNombre() + " " + alumnoLeido.getApellido() + " ha aprobado con un " + alumnoLeido.getNota();
                escritorTexto.newLine();
                escritorTexto.write(contenidoAlumno);
            }
        }
        escritorTexto.close();
    }

    public void run(String ruta) throws IOException, ClassNotFoundException {
        //escribirFlujo();
        //lectorFlujo(ruta);
        //escritorTipo();
        //lectorTipo(ruta);
        /*Alumno romera = new Alumno("Alejandro", 22, "2DAM", 10);
        escribirObjeto(romera);
         */
        //leerObjeto(Constants.rutaSalidaObjetos);
        escribirClaseAlumnos();
        leerObjeto(Constants.rutaSalidaAlumnos);
        escribirResumen();
        leerTexto(Constants.rutaSalidaResumen);
    }




}
