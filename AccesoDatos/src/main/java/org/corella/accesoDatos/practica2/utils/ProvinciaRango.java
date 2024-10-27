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

public class ProvinciaRango {
    private Map<Constants.Provincias, Map.Entry<Integer, Integer>> conjuntoProvincia = new HashMap<>();
    public ProvinciaRango() throws IOException {
        //Descomponemos nuestro fichero de texto para obtener Clave, valorinicial, valorfinal
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(Constants.rutaRangosProvincias));
        Scanner scannerLineas = new Scanner(lectorLineas);
        while (scannerLineas.hasNextLine()) {
            String[] linea = scannerLineas.nextLine().split(":");
            conjuntoProvincia.put(Constants.Provincias.valueOf(linea[0]), new AbstractMap.SimpleEntry<>(Integer.parseInt(linea[1]), Integer.parseInt(linea[2])));
        }
        lectorLineas.close();
        scannerLineas.close();
    }

    //Getter
    public Map<Constants.Provincias, Map.Entry<Integer, Integer>> getConjuntoProvincia() {
        return conjuntoProvincia;
    }

    //Escribir según tipo de provincia de entrada
    public void setProvincia(Constants.Provincias provincia, Integer limite) {
        switch (provincia) {
            case ALICANTE:
                setAlicante(limite);
                break;
            case CORDOBA:
                setCordoba(limite);
                break;
            case MADRID:
                setMadrid(limite);
                break;
            case VALLADOLID:
                setValladolid(limite);
                break;
        }
    }
    //Leer según tipo de provinca de entrada
    public Map.Entry<Integer, Integer> getProvincia(Constants.Provincias provincia) {
        return conjuntoProvincia.get(provincia);
    }

    //Setter (Cambiamos también el rango de la próxima entrada)
    private void setAlicante(Integer limite) {
        conjuntoProvincia.put(Constants.Provincias.ALICANTE, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.ALICANTE).getKey(), limite));
        conjuntoProvincia.put(Constants.Provincias.CORDOBA, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.ALICANTE).getValue() + 1, conjuntoProvincia.get(Constants.Provincias.CORDOBA).getValue()));
    }

    private void setCordoba(Integer limite) {
        conjuntoProvincia.put(Constants.Provincias.CORDOBA, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.CORDOBA).getKey(), limite));
        conjuntoProvincia.put(Constants.Provincias.MADRID, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.CORDOBA).getValue() + 1, conjuntoProvincia.get(Constants.Provincias.MADRID).getValue()));
    }

    private void setMadrid(Integer limite) {
        conjuntoProvincia.put(Constants.Provincias.MADRID, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.MADRID).getKey(), limite));
        conjuntoProvincia.put(Constants.Provincias.VALLADOLID, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.MADRID).getValue() + 1, conjuntoProvincia.get(Constants.Provincias.VALLADOLID).getValue()));
    }

    private void setValladolid(Integer limite) {
        conjuntoProvincia.put(Constants.Provincias.VALLADOLID, new AbstractMap.SimpleEntry<>(conjuntoProvincia.get(Constants.Provincias.VALLADOLID).getKey(), limite));
    }

    //Aplica los cambios a Provincias.txt
    public void flushProvincia() {
        try {
            BufferedWriter escritor = new Escritor().escritorLineas(new File(Constants.rutaRangosProvincias));
            escritor.write(String.valueOf(Constants.Provincias.ALICANTE) + ":" + conjuntoProvincia.get(Constants.Provincias.ALICANTE).getKey() + ":" + conjuntoProvincia.get(Constants.Provincias.ALICANTE).getValue());
            escritor.newLine();
            escritor.write(String.valueOf(Constants.Provincias.CORDOBA) + ":" + conjuntoProvincia.get(Constants.Provincias.CORDOBA).getKey() + ":" + conjuntoProvincia.get(Constants.Provincias.CORDOBA).getValue());
            escritor.newLine();
            escritor.write(String.valueOf(Constants.Provincias.MADRID) + ":" + conjuntoProvincia.get(Constants.Provincias.MADRID).getKey() + ":" + conjuntoProvincia.get(Constants.Provincias.MADRID).getValue());
            escritor.newLine();
            escritor.write(String.valueOf(Constants.Provincias.VALLADOLID) + ":" + conjuntoProvincia.get(Constants.Provincias.VALLADOLID).getKey() + ":" + conjuntoProvincia.get(Constants.Provincias.VALLADOLID).getValue());
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
