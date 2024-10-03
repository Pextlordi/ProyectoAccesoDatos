package org.corella.accesoDatos.claseFile;

import java.io.*;

public class FuncionesDirectorio {
    public void run(String directorio){
        System.out.println(directorio);
        File fichero = new File(directorio);
        if (!fichero.exists()) {
            System.out.println("Input no valida");
        } else {
            if (fichero.isDirectory()) {
                System.out.println("Es un directorio");
                recorrerDirectorio(fichero);
            } else {
                System.out.println("Es un fichero");
            }
        }
    }
    private void recorrerDirectorio(File directorio) throws NullPointerException{
        for(File fichero : directorio.listFiles()) {
            if(fichero.isFile()) {
                System.out.println("\t" + fichero.getName() + " " +  fichero.length());
            } else {
                System.out.println(fichero.getName());
                recorrerDirectorio(fichero);
            }
        }
    }
}
