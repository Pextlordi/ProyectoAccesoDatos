package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {
    @Id
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nom_emp", nullable = false, length = 40)
    private String nomEmp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_depto", nullable = false)
    private Departamento idDepto;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public Departamento getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(Departamento idDepto) {
        this.idDepto = idDepto;
    }

}