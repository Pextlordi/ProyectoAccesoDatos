package org.corella.accesoDatos.practica2.entities;

import javafx.util.Pair;
import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PersonalInfo implements Comparable<PersonalInfo> {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dirResidencia;
    private String dirFiscal;
    private Constants.Provincias provincia;
    private int codPostal;
    private String dni;
    private String numSS;

    //Constructor común (Con o sin 2 apellidos)
    public PersonalInfo(String [] campos) {
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
    //Getter
    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getDirResidencia() {
        return dirResidencia;
    }

    public String getDirFiscal() {
        return dirFiscal;
    }

    public Constants.Provincias getProvincia() {
        return provincia;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public String getDni() {
        return dni;
    }

    public String getNumSS() {
        return numSS;
    }

    //Formato de texto
    @Override
    public String toString() {
        if (!apellido2.isEmpty()) {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | Apellido2: " + apellido2 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | DNI: " + dni + " | NumSS: " + numSS;
        } else {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | DNI: " + dni + " | NumSS: " + numSS;
        }
    }

    public String getInfoAnonimizado() {
        if (!apellido2.isEmpty()) {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | Apellido2: " + apellido2 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | NumSS: " + numSS;
        } else {
            return "Nombre: " + nombre + " | Apellido1: " + apellido1 + " | DirResidencia: " + dirResidencia + "  | DirFiscal: " + dirFiscal + "  | Provincia: " + provincia + " | CodPostal: " + codPostal + " | NumSS: " + numSS;
        }
    }
    @Override
    public int compareTo(PersonalInfo o) {
        if(this.provincia.compareTo(o.getProvincia()) != 0) {
            return this.provincia.compareTo(o.getProvincia());
        } else {
            return this.getNombre().compareTo(o.getNombre());
        }
    }

    //Campos de nuestro fichero
    public static List<Pair<String, Integer>> getCamposPersonal() {
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

    //Obtención de datos financieros
    public FinancieroInfo getFinanciero() throws IOException {
        TreeSet<FinancieroInfo> financieroInfo = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero()).extractFinanciero();
        for (FinancieroInfo registroFinanciero : financieroInfo) {
            if (registroFinanciero.getDni().equals(this.dni)) {
                return registroFinanciero;
            }
        }
        return null;
    }
}
