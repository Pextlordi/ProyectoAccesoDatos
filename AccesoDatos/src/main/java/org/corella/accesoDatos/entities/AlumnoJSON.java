package org.corella.accesoDatos.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class AlumnoJSON {
    private String nombre;
    private ArrayList<String> asignaturas;
    private int edad;
    private boolean titula;
    public AlumnoJSON() {}
    public AlumnoJSON(String nombre, ArrayList<String> asignaturas, int edad, boolean titula) {
        this.nombre = nombre;
        this.asignaturas = asignaturas;
        this.edad = edad;
        this.titula = titula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public ArrayList<String> getAsignaturas() {
        return asignaturas;
    }
    public boolean getTitula() {
        return titula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setAsignaturas(ArrayList<String> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void setTitula(boolean titula) {
        this.titula = titula;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " Edad: " + edad + " Asignaturas: " + asignaturas + " Titula: " + titula;
    }
}
