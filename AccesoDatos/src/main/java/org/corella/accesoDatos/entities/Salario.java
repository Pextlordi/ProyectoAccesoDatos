package org.corella.accesoDatos.entities;

public class Salario {
    String cantidad;
    String fecha_entrada;
    String fecha_salida;
    public Salario(String cantidad, String fecha_entrada, String fecha_salida) {
        this.cantidad = cantidad;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
    }

    @Override
    public String toString() {
        return " - Salario de " + cantidad + " desde " + fecha_entrada + " hasta " + fecha_salida;
    }
}
