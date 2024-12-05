package org.corella.accesoDatos.hibernate.entities;

import javax.persistence.*;

@Entity
@Table(name = "sede")
public class Sede {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sede", nullable = false)
    private Integer id;

    @Column(name = "nom_sede", nullable = false, length = 20)
    private String nomSede;

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

}