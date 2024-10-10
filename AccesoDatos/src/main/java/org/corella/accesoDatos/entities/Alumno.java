package org.corella.accesoDatos.entities;

public class Alumno implements Comparable {
    private String nombre;
    private int edad;
    private String curso;
    private double nota;

    public Alumno(String nombre, int edad, String curso, double nota) {
        this.nombre = nombre;
        this.nota = nota;
        this.curso = curso;
        this.edad = edad;
    }
    public Alumno(String [] datosAlumno) {
        this.nombre = datosAlumno[0];
        this.nota = Double.parseDouble(datosAlumno[1]);
        this.curso = datosAlumno[2];
        this.edad = Integer.parseInt(datosAlumno[3]);
    }

    //getter and setter
    public double getNota() {
        return nota;
    }

    public String getCurso() {
        return curso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Alumno: " + nombre + " Nota: " + nota + " Curso: " + curso + " Edad: " + edad;
    }
    public String toStringCSV() {
        return nombre + "," + nota + "," + curso + "," + edad + ";";
    }
    @Override
    public int compareTo (Object o) {
        Alumno otro = (Alumno) o;
        if (this.edad < otro.getEdad()) {
            return -1;
        } else if (this.edad > otro.getEdad()) {
            return 1;
        } else {
            if(this.nota < otro.getNota()) {
                return 1;
            } else if (this.nota > otro.getNota()) {
                return -1;
            } else {
                return this.nombre.compareTo(otro.getNombre());
            }
        }
    }
}
