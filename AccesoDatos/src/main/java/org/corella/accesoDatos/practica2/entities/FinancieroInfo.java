package org.corella.accesoDatos.practica2.entities;

import javafx.util.Pair;
import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class FinancieroInfo implements Comparable<FinancieroInfo> {
    private int id;
    private String dni;
    private double balance;
    private int numCuenta;
    private Constants.Irpf irpf;
    private double aportacionesONG;
    private double aportacionesFondo;
    private Constants.Fondo tipoFondo;

    //Constructor común (Con o sin Fondos)
    public FinancieroInfo(String [] campos) {
        if (campos.length < 8) {
            String [] camposCopia = new String[campos.length+1];
            System.arraycopy( campos, 0, camposCopia, 0, campos.length);
            camposCopia[camposCopia.length-1] = null;
            campos = camposCopia;
        }
        this.id = Integer.parseInt(campos[0]);
        this.dni = campos[1];
        this.balance = Double.parseDouble(campos[2]);
        this.numCuenta = Integer.parseInt(campos[3]);
        //Acepta un numero o String como entrada
        try {
            this.irpf = Constants.Irpf.valueOf("IRPF_" + campos[4]);
        } catch (IllegalArgumentException e) {
            this.irpf = Constants.Irpf.valueOf(campos[4]);
        }

        this.aportacionesONG = Double.parseDouble(campos[5]);
        this.aportacionesFondo = Double.parseDouble(campos[6]);
        //Analizamos si tiene o no Fondo sea nulo o esté vacío
        if (campos[7] != null) {
            if (!campos[7].isEmpty()) {
                this.tipoFondo = Constants.Fondo.valueOf(campos[7].toUpperCase());
            } else {
                this.tipoFondo = null;
            }
        } else {
            this.tipoFondo = null;
        }
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public double getBalance() {
        return balance;
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public Constants.Irpf getIrpf() {
        return irpf;
    }

    public double getAportacionesONG() {
        return aportacionesONG;
    }

    public double getAportacionesFondo() {
        return aportacionesFondo;
    }

    public Constants.Fondo getTipoFondo() {
        return tipoFondo;
    }

    //Formato de texto
    @Override
    public String toString() {
        if(tipoFondo != null) {
            return "ID: " + id + " | Balance: " + balance + " | Numero de cuenta: " + numCuenta + " | Irpf: " + irpf + " | Aportaciones ONG: " + aportacionesONG + " | Aportaciones Fondo de "+ tipoFondo + ": " + aportacionesFondo;
        } else {
            return "ID: " + id + " | Balance: " + balance + " | Numero de cuenta: " + numCuenta + " | Irpf: " + irpf + " | Aportaciones ONG: " + aportacionesONG;
        }

    }

    @Override
    public int compareTo(FinancieroInfo o) {
        if(this.irpf.compareTo(o.irpf) != 0) {
            return this.irpf.compareTo(o.irpf);
        } else {
            if(this.id < o.id) {
                return -1;
            } else if(this.id > o.id) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //Campos de nuestro fichero
    public static List<Pair<String, Integer>> getCamposFinanciero() {
        List<Pair<String, Integer>> campos = new ArrayList<>();
        campos.add(new Pair<>("id", 3));
        campos.add(new Pair<>("dni", 9));
        campos.add(new Pair<>("balance", 10));
        campos.add(new Pair<>("numCuenta", 8));
        campos.add(new Pair<>("irpf", 8));
        campos.add(new Pair<>("aportacionesONG", 10));
        campos.add(new Pair<>("aportacionesFondo", 10));
        campos.add(new Pair<>("tipoFondo", 10));
        return campos;
    }

    //Obtención de datos personales
    public PersonalInfo getFinanciero() throws IOException {
        TreeSet<PersonalInfo> personalInfo = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, PersonalInfo.getCamposPersonal()).extractPersonal();
        for (PersonalInfo registroPersonal : personalInfo) {
            if (registroPersonal.getDni().equals(this.dni)) {
                return registroPersonal;
            }
        }
        return null;
    }
}
