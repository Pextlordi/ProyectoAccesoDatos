package org.corella.accesoDatos.practica2.utils;

import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class IrpfRango {
    private Map.Entry<Integer, Integer> irpf5;
    private Map.Entry<Integer, Integer> irpf10;
    private Map.Entry<Integer, Integer> irpf15;

    public IrpfRango() throws IOException {
        BufferedReader lectorLineas = new Lector().lectorLineas(new File("src/main/java/org/corella/accesoDatos/practica2/resources/Irpf.txt"));
        this.irpf5 = new AbstractMap.SimpleEntry<>(1, Integer.parseInt(lectorLineas.readLine().split(":")[1]));
        this.irpf10 = new AbstractMap.SimpleEntry<>(irpf5.getValue()+1, Integer.parseInt(lectorLineas.readLine().split(":")[1]));
        this.irpf15 = new AbstractMap.SimpleEntry<>(irpf10.getValue()+1, Integer.parseInt(lectorLineas.readLine().split(":")[1]));
    }

    public Map.Entry<Integer, Integer> getIrpf5() {
        return irpf5;
    }

    public Map.Entry<Integer, Integer> getIrpf10() {
        return irpf10;
    }

    public Map.Entry<Integer, Integer> getIrpf15() {
        return irpf15;
    }

    public void setIrpf5(Map.Entry<Integer, Integer> irpf5) {
        this.irpf5 = irpf5;
    }

    public void setIrpf10(Map.Entry<Integer, Integer> irpf10) {
        this.irpf10 = irpf10;
    }

    public void setIrpf15(Map.Entry<Integer, Integer> irpf15) {
        this.irpf15 = irpf15;
    }
}
