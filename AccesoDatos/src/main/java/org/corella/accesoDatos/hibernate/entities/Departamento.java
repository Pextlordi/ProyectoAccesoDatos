package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "departamento")
public class Departamento implements Serializable {
    @Id
    @Column(name = "id_depto", nullable = false)
    private Integer id;

    @Column(name = "nom_depto", nullable = false, length = 32)
    private String nomDepto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private org.corella.accesoDatos.hibernate.entities.Sede idSede;

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

    public org.corella.accesoDatos.hibernate.entities.Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(org.corella.accesoDatos.hibernate.entities.Sede idSede) {
        this.idSede = idSede;
    }

}