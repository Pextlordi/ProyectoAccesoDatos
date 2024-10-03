package org.corella.accesoDatos.claseFile;

import java.io.*;

public class FileReaderWriter {
    public FileReaderWriter(String pathEscitura) {
        this.pathEscitura = pathEscitura;
    }
    private String pathEscitura;
    public void run() {
        try {
            File fichero = new File(pathEscitura);
            if(!fichero.exists()) {
                throw new FileNotFoundException("La ruta no existe");
            } else if(fichero.isDirectory()) {
                throw new FileNotFoundException("La ruta indicada es un directorio");
            } else {
                FileReader lector = new FileReader(fichero);
                contador(lector);
            }
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println();
    }

    private void contador(FileReader lector) throws IOException {
        int contadorVocalA , contadorVocalE, contadorVocalI, contadorVocalO, contadorVocalU;
        contadorVocalA = contadorVocalE = contadorVocalI = contadorVocalO = contadorVocalU = 0;
        int indice = 0;
        while ((indice = lector.read()) != -1) {
            char letraLeida = (char) indice;
            switch (letraLeida) {
                case 'A':
                case 'a': contadorVocalA++; break;
                case 'E':
                case 'e': contadorVocalE++; break;
                case 'I':
                case 'i': contadorVocalI++; break;
                case 'O':
                case 'o': contadorVocalO++; break;
                case 'U':
                case 'u': contadorVocalU++; break;
            }
        }
        String textoResultado = "\nEl numero de \"a\" es: " + contadorVocalA +
                                "\nEl numero de \"e\" es: " + contadorVocalE +
                                "\nEl numero de \"i\" es: " + contadorVocalI +
                                "\nEl numero de \"o\" es: " + contadorVocalO +
                                "\nEl numero de \"u\" es: " + contadorVocalU;
        escribirContadores(textoResultado);
    }
    private void escribirContadores (String contenido) throws IOException {
        FileWriter escritor = new FileWriter(pathEscitura, true);
        for (char caracterEscribir : contenido.toCharArray()) {
            escritor.write(caracterEscribir);
        }
        escritor.close();
        System.out.println("Escritura completada");
    }
}
