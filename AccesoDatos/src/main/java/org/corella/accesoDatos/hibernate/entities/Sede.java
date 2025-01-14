package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sede")
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "nom_sede", nullable = false, length = 20)
    private String nomSede;

    @OneToMany(mappedBy = "sede")
    private Set<Departamento> departamentosSede;

    public Sede() {}

    public Sede(String nomSede) {
        this.nomSede = nomSede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public Set<Departamento> getDepartamentosSede() {
        return departamentosSede;
    }

    public void setDepartamentosSede(Set<Departamento> departamentosSede) {
        this.departamentosSede = departamentosSede;
    }
}