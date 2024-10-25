package org.corella.accesoDatos.practica2;

import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.utils.IrpfRango;

public class Main {
    public static void main(String[] args) {
        System.out.println(IrpfRango.getIrpf5().getKey() + " " + IrpfRango.getIrpf5().getValue());
        System.out.println(IrpfRango.getIrpf10().getKey() + " " + IrpfRango.getIrpf10().getValue());
        System.out.println(IrpfRango.getIrpf15().getKey() + " " + IrpfRango.getIrpf15().getValue());
    }
}
