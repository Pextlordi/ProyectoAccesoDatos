package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "proyecto_sede")
public class ProyectoSede implements Serializable {
    @EmbeddedId
    private ProyectoSedeId id;

    @MapsId("idProy")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proy", nullable = false)
    private Proyecto idProy;

    @MapsId("idSede")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sede", nullable = false)
    private org.corella.accesoDatos.hibernate.entities.Sede idSede;

    @Basic
    @Column(name = "f_inicio", nullable = false)
    private LocalDate fInicio;

    @Basic
    @Column(name = "f_fin")
    private LocalDate fFin;

    public ProyectoSedeId getId() {
        return id;
    }

    public void setId(ProyectoSedeId id) {
        this.id = id;
    }

    public Proyecto getIdProy() {
        return idProy;
    }

    public void setIdProy(Proyecto idProy) {
        this.idProy = idProy;
    }

    public org.corella.accesoDatos.hibernate.entities.Sede getIdSede() {
        return idSede;
    }

    public void setIdSede(org.corella.accesoDatos.hibernate.entities.Sede idSede) {
        this.idSede = idSede;
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

}