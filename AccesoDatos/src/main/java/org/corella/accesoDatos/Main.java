package org.corella.accesoDatos;

import org.corella.accesoDatos.applications.ConectorMySQL;
import org.corella.accesoDatos.applications.LeerEscribirObjetos;
import org.corella.accesoDatos.applications.ManejoCSV;
import org.corella.accesoDatos.applications.ManejoXML;
import org.corella.accesoDatos.hibernate.app.ORM_Conexion;
import org.corella.accesoDatos.hibernate.util.HibernateUtil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ORM_Conexion conHibernate = new ORM_Conexion();
        conHibernate.run();
    }
}
