package org.corella.accesoDatos.practica2;

import javafx.util.Pair;
import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.entities.FiscalInfo;
import org.corella.accesoDatos.practica2.entities.PersonaInfo;
import org.corella.accesoDatos.practica2.utils.IrpfRango;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println(IrpfRango.getIrpf5().getKey() + " " + IrpfRango.getIrpf5().getValue());
        System.out.println(IrpfRango.getIrpf10().getKey() + " " + IrpfRango.getIrpf10().getValue());
        System.out.println(IrpfRango.getIrpf15().getKey() + " " + IrpfRango.getIrpf15().getValue());
         */
        try {
            TreeSet<PersonaInfo> infoPersonal = leerCSVPersonal("src/main/java/org/corella/accesoDatos/practica2/resources/Personal.csv");
            TreeSet<FiscalInfo> infoFiscal = leerCSVFiscal("src/main/java/org/corella/accesoDatos/practica2/resources/Fiscal.csv");
            for(PersonaInfo persona : infoPersonal) {
                System.out.println(persona);
            }
            for (FiscalInfo persona : infoFiscal) {
                System.out.println(persona);
            }
            IrpfRango tabla = new IrpfRango();
            System.out.println(tabla.getIrpf5());
            System.out.println(tabla.getIrpf10());
            System.out.println(tabla.getIrpf15());
            guardarInfoPersonal(infoPersonal);
            guardarInfoFiscal(infoFiscal);
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

    private static List<Pair<String, Integer>> getCamposPersonal() {
        List<Pair<String, Integer>> campos = new ArrayList<>();
        campos.add(new Pair<>("nombre", 15));
        campos.add(new Pair<>("apellido1", 15));
        campos.add(new Pair<>("apellido2", 15));
        campos.add(new Pair<>("dirResidencia", 40));
        campos.add(new Pair<>("dirFiscal", 40));
        campos.add(new Pair<>("provincia", 15));
        campos.add(new Pair<>("codPostal", 5));
        campos.add(new Pair<>("dni", 9));
        campos.add(new Pair<>("numSS", 11));
        return campos;
    }
    private static List<Pair<String, Integer>> getCamposFiscal() {
        List<Pair<String, Integer>> campos = new ArrayList<>();
        campos.add(new Pair<>("id", 3));
        campos.add(new Pair<>("dni", 9));
        campos.add(new Pair<>("balance", 10));
        campos.add(new Pair<>("numCuenta", 8));
        campos.add(new Pair<>("irpf", 2));
        campos.add(new Pair<>("aportacionesONG", 10));
        campos.add(new Pair<>("aportacionesFondo", 10));
        campos.add(new Pair<>("tipoFondo", 10));
        return campos;
    }

    private static void guardarInfoPersonal(TreeSet<PersonaInfo> infoPersonal) throws IOException {
        List<Pair<String, Integer>> campos = getCamposPersonal();
        FicheroAccesoAleatorio lectorEscritor = new FicheroAccesoAleatorio("src/main/java/org/corella/accesoDatos/practica2/out/PersonalData.dat", campos);
        for (PersonaInfo personaEscribir : infoPersonal) {
            Map<String, String> registro = new HashMap<>();
            registro.put(campos.get(0).getKey(), personaEscribir.getNombre());
            registro.put(campos.get(1).getKey(), personaEscribir.getApellido1());
            registro.put(campos.get(2).getKey(), personaEscribir.getApellido2());
            registro.put(campos.get(3).getKey(), personaEscribir.getDirResidencia());
            registro.put(campos.get(4).getKey(), personaEscribir.getDirFiscal());
            registro.put(campos.get(5).getKey(), String.valueOf(personaEscribir.getProvincia()));
            registro.put(campos.get(6).getKey(), String.valueOf(personaEscribir.getCodPostal()));
            registro.put(campos.get(7).getKey(), personaEscribir.getDni());
            registro.put(campos.get(8).getKey(), personaEscribir.getNumSS());
            lectorEscritor.insertar(registro);
        }

    }

    private static void guardarInfoFiscal(TreeSet<FiscalInfo> infoFiscal) throws IOException {
        FicheroAccesoAleatorio lectorEscitor = new FicheroAccesoAleatorio("src/main/java/org/corella/accesoDatos/practica2/out/FiscalData.dat", getCamposFiscal());
    }
}
