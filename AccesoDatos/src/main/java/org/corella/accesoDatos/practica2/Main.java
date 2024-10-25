package org.corella.accesoDatos.practica2;

import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.entities.FiscalInfo;
import org.corella.accesoDatos.practica2.entities.PersonaInfo;
import org.corella.accesoDatos.practica2.utils.IrpfRango;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println(IrpfRango.getIrpf5().getKey() + " " + IrpfRango.getIrpf5().getValue());
        System.out.println(IrpfRango.getIrpf10().getKey() + " " + IrpfRango.getIrpf10().getValue());
        System.out.println(IrpfRango.getIrpf15().getKey() + " " + IrpfRango.getIrpf15().getValue());
         */
        try {
            leerCSVPersonal("src/main/java/org/corella/accesoDatos/practica2/resources/Personal.csv");
            leerCSVFiscal("src/main/java/org/corella/accesoDatos/practica2/resources/Fiscal.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static TreeSet<PersonaInfo> leerCSVPersonal(String rutaFichero) throws IOException {
        TreeSet<PersonaInfo> conjuntoPersonal = new TreeSet<>();
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(rutaFichero));
        lectorLineas.readLine();
        String lineaLeida;
        while ((lineaLeida = lectorLineas.readLine()) != null) {
            conjuntoPersonal.add(new PersonaInfo(lineaLeida.split(",")));
        }
        return conjuntoPersonal;
    }
    private static TreeSet<FiscalInfo> leerCSVFiscal(String rutaFichero) throws IOException {
        TreeSet<FiscalInfo> conjuntoFiscal = new TreeSet<>();
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(rutaFichero));
        lectorLineas.readLine();
        String lineaLeida;
        while ((lineaLeida = lectorLineas.readLine()) != null) {
            conjuntoFiscal.add(new FiscalInfo(lineaLeida.split(",")));
        }
        return conjuntoFiscal;
    }
}
