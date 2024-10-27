package org.corella.accesoDatos.practica2.utils;

import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IrpfRango {
    private Map<Constants.Irpf, Map.Entry<Integer, Integer>> conjuntoIrpf = new HashMap<>();
    public IrpfRango() throws IOException {
        //Descomponemos nuestro fichero de texto para obtener Clave, valorinicial, valorfinal
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(Constants.rutaRangosIrpf));
        Scanner scannerLineas = new Scanner(lectorLineas);
        while (scannerLineas.hasNextLine()) {
            String[] linea = scannerLineas.nextLine().split(":");
            conjuntoIrpf.put(Constants.Irpf.valueOf(linea[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(linea[1]), Integer.parseInt(linea[2])));
        }
        lectorLineas.close();
        scannerLineas.close();
    }

    //Escribir según tipo de IRPF de entrada
    public void setIrpf(Constants.Irpf tipoIrpf, Integer limite) {
        switch (tipoIrpf) {
            case IRPF_5:
                setIrpf5(limite);
                break;
            case IRPF_10:
                setIrpf10(limite);
                break;
            case IRPF_15:
                setIrpf15(limite);
                break;
            default:
                break;
        }
    }

    //Leer según tipo de IRPF de entrada
    public Map.Entry<Integer, Integer> getIrpf(Constants.Irpf tipoIrpf) {
        return conjuntoIrpf.get(tipoIrpf);
    }

    //Setter (Cambiamos también el rango de la próxima entrada)
    public void setIrpf5(Integer posicion) {
        conjuntoIrpf.put(Constants.Irpf.IRPF_5, new AbstractMap.SimpleEntry<>(conjuntoIrpf.get(Constants.Irpf.IRPF_5).getKey(), posicion));
        conjuntoIrpf.put(Constants.Irpf.IRPF_10, new AbstractMap.SimpleEntry<>(conjuntoIrpf.get(Constants.Irpf.IRPF_5).getValue() + 1, conjuntoIrpf.get(Constants.Irpf.IRPF_10).getValue()));
    }

    public void setIrpf10(Integer posicion) {
        conjuntoIrpf.put(Constants.Irpf.IRPF_10, new AbstractMap.SimpleEntry<>(conjuntoIrpf.get(Constants.Irpf.IRPF_10).getKey(), posicion));
        conjuntoIrpf.put(Constants.Irpf.IRPF_15, new AbstractMap.SimpleEntry<>(conjuntoIrpf.get(Constants.Irpf.IRPF_10).getValue() + 1, conjuntoIrpf.get(Constants.Irpf.IRPF_15).getValue()));
    }

    public void setIrpf15(Integer posicion) {
        conjuntoIrpf.put(Constants.Irpf.IRPF_15, new AbstractMap.SimpleEntry<>(conjuntoIrpf.get(Constants.Irpf.IRPF_15).getKey(), posicion));
    }

    //Aplica los cambios a Irpf.txt
    public void flushIrpf() {
        try {
            BufferedWriter escritor = new Escritor().escritorLineas(new File(Constants.rutaRangosIrpf));
            escritor.write(String.valueOf(Constants.Irpf.IRPF_5) + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_5).getKey() + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_5).getValue());
            escritor.newLine();
            escritor.write(String.valueOf(Constants.Irpf.IRPF_10) + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_10).getKey() + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_10).getValue());
            escritor.newLine();
            escritor.write(String.valueOf(Constants.Irpf.IRPF_15) + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_15).getKey() + ":" + conjuntoIrpf.get(Constants.Irpf.IRPF_15).getValue());
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
