package org.corella.accesoDatos.practica2.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class IrpfRango {
    private static Map.Entry<Integer, Integer> irpf5 = new AbstractMap.SimpleEntry<>(1, 6);
    private static Map.Entry<Integer, Integer> irpf10 = new AbstractMap.SimpleEntry<>(irpf5.getValue()+1, 10);
    private static Map.Entry<Integer, Integer> irpf15 = new AbstractMap.SimpleEntry<>(irpf10.getValue()+1, 15);

    public static Map.Entry<Integer, Integer> getIrpf5() {
        return irpf5;
    }

    public static Map.Entry<Integer, Integer> getIrpf10() {
        return irpf10;
    }

    public static Map.Entry<Integer, Integer> getIrpf15() {
        return irpf15;
    }
}
