package org.corella.accesoDatos.practica2.entities;

import org.corella.accesoDatos.practica2.utils.Constants;

import java.util.Arrays;

public class FiscalInfo {
    private int id;
    private String dni;
    private double balance;
    private int numCuenta;
    private Constants.Irpf irpf;
    private double aportacionesONG;
    private double aportacionesFondo;
    private Constants.Fondo tipoFondo;

    public FiscalInfo(String [] campos) {
        if (campos.length < 8) {
            String [] camposCopia = new String[campos.length+1];
            System.arraycopy( campos, 0, camposCopia, 0, campos.length);
            camposCopia[camposCopia.length-1] = null;
        }
        this.id = Integer.parseInt(campos[0]);
        this.dni = campos[1];
        this.balance = Double.parseDouble(campos[2]);
        this.numCuenta = Integer.parseInt(campos[3]);
        this.irpf = Constants.Irpf.valueOf(campos[4]);
        this.aportacionesONG = Double.parseDouble(campos[5]);
        this.aportacionesFondo = Double.parseDouble(campos[6]);
        this.tipoFondo = Constants.Fondo.valueOf(campos[7]);
    }

}
