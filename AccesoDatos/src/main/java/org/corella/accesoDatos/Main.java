package org.corella.accesoDatos;

import org.corella.accesoDatos.applications.LeerEscribirObjetos;
import org.corella.accesoDatos.applications.ManejoCSV;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*FuncionesDirectorio fd = new FuncionesDirectorio();
        fd.run(args[0]);*/
        /*FileReaderWriter frw = new FileReaderWriter(args[0]);
        frw.run();*/
        /*ManejoCSV manejoCSV = new ManejoCSV();
        manejoCSV.run(args[0]);*/
        LeerEscribirObjetos app = new LeerEscribirObjetos();
        app.run("");
    }

    /*
    B1
        Inicialización de variables y asignación de recursos
    B2
        Try catch o cuerpo del codigo
    B3
        Finalización y liberación de recursos
    */

}
