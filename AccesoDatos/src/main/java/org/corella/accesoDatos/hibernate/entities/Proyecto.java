package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    @Id
    @Column(name = "id_proy", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Basic
    @Column(name = "f_fin")
    private LocalDate fFin;

    @Basic
    @Column(name = "nom_proy", nullable = false, length = 20)
    private String nomProy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFInicio() {
        return fInicio;
    }

    public void setFInicio(LocalDate fInicio) {
        this.fInicio = fInicio;
    }

    public LocalDate getFFin() {
        return fFin;
    }

    public void setFFin(LocalDate fFin) {
        this.fFin = fFin;
    }

    public String getNomProy() {
        return nomProy;
    }

    public void setNomProy(String nomProy) {
        this.nomProy = nomProy;
    }

}