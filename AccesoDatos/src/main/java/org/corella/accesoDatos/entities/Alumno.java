package org.corella.accesoDatos.entities;

import java.io.Serializable;

public class Alumno implements Serializable, Comparable {

    private String nombre;
    private String apellido;
    private double altura;
    private int edad;
    private int nota;

    public Alumno(String nombre, String apellido, double altura, int edad, int nota) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.altura = altura;
        this.edad = edad;
        this.nota = nota;
    }

    public Alumno(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        //Generación de altura entre 1 y 1.97
        this.altura = (Math.random() * 0.87) + 1.1;
        //Aproximación con 2 dígitos
        this.altura = Math.round(this.altura * 100.0) / 100.0;
        //Filtro de edad
        if (this.altura <= 1.35) {
            this.edad = 18;
        } else {
            //Extraigo los dígitos aproximando la mitad hacia abajo
            this.edad = (int) Math.floor((altura - 1.0) * 100 / 2);
        }
        this.nota = edad/10*2;
    }

    public Alumno(String [] datosAlumno) {
        this.nombre = datosAlumno[0];
        this.apellido = datosAlumno[1];
        this.altura = Double.parseDouble(datosAlumno[2]);
        this.edad = Integer.parseInt(datosAlumno[3]);
        this.nota = Integer.parseInt(datosAlumno[4]);
    }

    //getter and setter
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public double getAltura() {
        return altura;
    }
    public int getEdad() {
        return edad;
    }
    public int getNota() {
        return nota;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Apellido: " + apellido + " | Altura: " + altura +" | Edad: " + edad + " | Nota: " + nota;
    }
    public String toStringCSV() {
        return nombre + "," + apellido + "," + altura + "," + edad + "," + nota;
    }
    @Override
    public int compareTo (Object o) {
        Alumno otro = (Alumno) o;
        int compara;
        if ((compara = this.nombre.compareTo(otro.getNombre())) != 0) {
            return compara;
        } else {
            if ((compara = this.apellido.compareTo(otro.getApellido())) != 0) {
                return compara;
            } else {
                return 0;
            }
        }
    }
}
