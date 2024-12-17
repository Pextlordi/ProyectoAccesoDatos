package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "departamento")
public class Departamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_depto", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "nom_depto", nullable = false, length = 32)
    private String nomDepto;

    /*
        @Basic
        @Column(name = "id_sede")
        private int sedeId;
     */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private Sede sede;

    @OneToMany(mappedBy = "idDepto")
    private Set<Empleado> empleadosDepartamento;

    public Departamento() {}

    public Departamento(String nomDepto, Sede sede) {
        this.nomDepto = nomDepto;
        this.sede = sede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomDepto() {
        return nomDepto;
    }

    public void setNomDepto(String nomDepto) {
        this.nomDepto = nomDepto;
    }

    public Sede getIdSede() {
        return sede;
    }

    public void setIdSede(Sede sede) {
        this.sede = sede;
    }

    public Set<Empleado> getEmpleadosDepartamento() {
        return empleadosDepartamento;
    }

    public void setEmpleadosDepartamento(Set<Empleado> empleadosDepartamento) {
        this.empleadosDepartamento = empleadosDepartamento;
    }
}