package org.corella.accesoDatos.entities;

public class JugBaloncestoBD {
    private String nombre;
    private String posicion;
    private double puntosPorPartido;

    public JugBaloncestoBD(String nombre, String posicion, double puntosPorPartido) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.puntosPorPartido = puntosPorPartido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public double getPuntosPorPartido() {
        return puntosPorPartido;
    }
}
