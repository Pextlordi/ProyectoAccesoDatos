package org.corella.accesoDatos.practica2.entities;

import org.corella.accesoDatos.practica2.utils.Constants;

import java.util.Arrays;

public class FiscalInfo implements Comparable<FiscalInfo> {
    private int id;
    private String dni;
    private double balance;
    private int numCuenta;
    private Constants.Irpf irpf;
    private double aportacionesONG;
    private double aportacionesFondo;
    private Constants.Fondo tipoFondo;
    //Constructor común (Con o sin Fondos)
    public FiscalInfo(String [] campos) {
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
        this.irpf = Constants.Irpf.valueOf("IRPF_" + campos[4]);
        this.aportacionesONG = Double.parseDouble(campos[5]);
        this.aportacionesFondo = Double.parseDouble(campos[6]);
        if (campos[7] != null) {
            this.tipoFondo = Constants.Fondo.valueOf(campos[7].toUpperCase());
        } else {
            this.tipoFondo = null;
        }

    }

    @Override
    public String toString() {
        if(tipoFondo != null) {
            return "ID: " + id + " | Balance: " + balance + " | Numero de cuenta: " + numCuenta + " | Irpf: " + irpf + " | Aportaciones ONG: " + aportacionesONG + " | Aportaciones Fondo de "+ tipoFondo + ": " + aportacionesFondo;
        } else {
            return "ID: " + id + " | Balance: " + balance + " | Numero de cuenta: " + numCuenta + " | Irpf: " + irpf + " | Aportaciones ONG: " + aportacionesONG;
        }

    }

    @Override
    public int compareTo(FiscalInfo o) {
        if(this.irpf.compareTo(o.irpf) != 0) {
            return this.irpf.compareTo(o.irpf);
        } else {
            return this.dni.compareTo(o.dni);
        }
    }
}
