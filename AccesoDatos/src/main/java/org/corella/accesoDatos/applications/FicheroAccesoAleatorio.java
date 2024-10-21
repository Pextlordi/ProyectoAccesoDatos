package org.corella.accesoDatos.applications;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javafx.util.*;

public class FicheroAccesoAleatorio {
    private File file;
    private List<Pair<String, Integer>> campos;
    private long longitudRegistros = 0;
    private long numRegistros = 0;
    public FicheroAccesoAleatorio(String nombreFichero, List<Pair<String, Integer>> campos) throws IOException {
        this.file = new File(nombreFichero);
        this.campos = campos;
        for(Pair<String, Integer> campo : campos) {
            this.longitudRegistros += campo.getValue();
        }
        if (file.exists()) {
            this.numRegistros = file.length()/this.longitudRegistros;
        }
    }

    public FicheroAccesoAleatorio(){}

    public long getNumRegistros() {
        return numRegistros;
    }


    public void insertar(Map<String, String> registro) throws IOException {
        insertar(registro, this.numRegistros++);
    }

    public void insertar(Map<String, String> registro, long posicion) throws IOException {
        RandomAccessFile escritorAleatorio = new RandomAccessFile(this.file, "rwd");
        escritorAleatorio.seek(posicion * this.longitudRegistros);
        for (Pair<String, Integer> campo : this.campos) {
            String nombreCampo = campo.getKey();
            Integer longitudCampo = campo.getValue();
            String valorCampo = registro.get(nombreCampo);
            if (valorCampo == null) {
                valorCampo = "";
            }
            String valorCampForm = String.format("%1$-" + longitudCampo + "s", valorCampo);
            escritorAleatorio.write(valorCampForm.getBytes(StandardCharsets.UTF_8), 0, longitudCampo);
            this.numRegistros++;
        }
        escritorAleatorio.close();
    }

    public void leer(long postition) throws IOException {
        RandomAccessFile escritorAleatorio = new RandomAccessFile(this.file, "rwd");
        escritorAleatorio.seek(postition * this.longitudRegistros);
        escritorAleatorio.readLine();
    }

    public void run() {
        List<Pair<String, Integer>> campos = new ArrayList<>();
        campos.add(new Pair<>("DNI", 9));
        campos.add(new Pair<>("NOMBRE", 32));
        campos.add(new Pair<>("NACIONALIDAD", 25));
        try {
            FicheroAccesoAleatorio escritor = new FicheroAccesoAleatorio("src/main/resources/FicheroOutRandomAccess.dat", campos);
            Map<String, String> registros = new HashMap();
            registros.put("DNI","12345678A");
            registros.put("NOMBRE","JUAN");
            registros.put("NACIONALIDAD","ISLAS BRITANICAS VIRGENES");
            escritor.insertar(registros);
            registros.clear();
            registros.put("DNI","34644443B");
            registros.put("NOMBRE","PEPE");
            registros.put("NACIONALIDAD","ESPAÑOL");
            escritor.insertar(registros);
            registros.clear();
            registros.put("DNI","33432642N");
            registros.put("NOMBRE","MARTIN");
            registros.put("NACIONALIDAD","BOLIVIANO");
            escritor.insertar(registros);
            registros.clear();
            registros.put("DNI","74883938K");
            registros.put("NOMBRE","ALBERTA");
            registros.put("NACIONALIDAD","RUSA");
            escritor.insertar(registros);
            registros.clear();
            registros.put("DNI","27357238P");
            registros.put("NOMBRE","");
            registros.put("NACIONALIDAD","PERUANA");
            escritor.insertar(registros);
            leer(1);
        } catch (IOException e) {
            System.err.println("ERROR de E/S: " + e.getMessage());
        }
    }
}
