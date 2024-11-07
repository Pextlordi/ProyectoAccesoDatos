package org.corella.accesoDatos;

import org.corella.accesoDatos.applications.LeerEscribirObjetos;
import org.corella.accesoDatos.applications.ManejoCSV;
import org.corella.accesoDatos.applications.ManejoXML;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ManejoXML manejoXML = new ManejoXML();
        manejoXML.run();
    }


}
