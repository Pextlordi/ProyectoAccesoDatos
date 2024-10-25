package org.corella.accesoDatos.practica2.entities;

import org.corella.accesoDatos.practica2.utils.Constants;

import java.util.Arrays;

public class PersonaInfo {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dirResidencia;
    private String dirFiscal;
    private Constants.Provincias provincia;
    private int codPostal;
    private String dni;
    private String numSS;

    //Constructor común (1 y 2 apellidos)
    public PersonaInfo(String [] campos) {
        if (campos.length < 9) {
            String [] camposCopia = new String[campos.length+1];
            System.arraycopy(campos, 0, camposCopia, 0, 2);
            camposCopia[2] = null;
            System.arraycopy(campos, 2, camposCopia, 3, campos.length-2);
            campos = camposCopia;
        }
        this.nombre = campos[0];
        this.apellido1 = campos[1];
        this.apellido2 = campos[2];
        this.dirResidencia = campos[3];
        this.dirFiscal = campos[4];
        this.provincia = Constants.Provincias.valueOf(campos[5].toUpperCase());
        this.codPostal = Integer.parseInt(campos[6]);
        this.dni = campos[7];
        this.numSS = campos[8];
    }

    @Override
    public String toString() {
        if (!apellido2.isEmpty()) {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | Apellido2: " + apellido2 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | DNI: " + dni + " | NumSS: " + numSS;
        } else {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | DNI: " + dni + " | NumSS: " + numSS;
        }
    }
}
