package org.corella.accesoDatos;

import org.corella.accesoDatos.hibernate.app.ORM_Conexion;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ORM_Conexion conHibernate = new ORM_Conexion();
        conHibernate.run();
    }
}
