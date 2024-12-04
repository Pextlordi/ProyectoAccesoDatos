package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "empleado_datos_prof")
public class EmpleadoDatosProf {
    @Id
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dni", nullable = false)
    private Empleado empleado;

    @Column(name = "categoria", nullable = false, length = 2)
    private String categoria;

    @Column(name = "sueldo_bruto_anual", precision = 8, scale = 2)
    private BigDecimal sueldoBrutoAnual;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getSueldoBrutoAnual() {
        return sueldoBrutoAnual;
    }

    public void setSueldoBrutoAnual(BigDecimal sueldoBrutoAnual) {
        this.sueldoBrutoAnual = sueldoBrutoAnual;
    }

}