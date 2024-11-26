package org.corella.accesoDatos.entities;

import java.util.ArrayList;

public class Empleado {
    String num;
    String fecha_nacimiento;
    String nombre;
    String apellido;
    String genero;
    String fecha_contrato;
    ArrayList<Salario> salarios;
    public Empleado(String num, String fecha_nacimiento, String nombre, String apellido, String genero, String fecha_contrato) {
        this.num = num;
        this.fecha_nacimiento = fecha_nacimiento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fecha_contrato = fecha_contrato;
        salarios = new ArrayList<>();
    }

    public String getNum() {
        return num;
    }

    public void agregarSalario(Salario salario) {
        salarios.add(salario);
    }


    @Override
    public String toString() {
        String texto = "Empleado: " + num + " Nombre: " + nombre + " Apellido: " + apellido + " Genero: " + genero + " Fecha de Contrato: " + fecha_contrato + "\nSalarios:";
        for (Salario salario : salarios) {
            texto = texto +"\n" + salario;
        }
        return texto;
    }
}
