package org.corella.accesoDatos.applications;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.sun.source.tree.Tree;
import javafx.util.*;
import org.corella.accesoDatos.practica2.entities.FinancieroInfo;
import org.corella.accesoDatos.practica2.entities.PersonalInfo;
import org.corella.accesoDatos.practica2.utils.Constants;

public class FicheroAccesoAleatorio {
    private File file;
    private List<Pair<String, Integer>> campos;
    private long longitudRegistros = 1; //Incluimos salto de línea
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
        }
        escritorAleatorio.writeBytes(System.lineSeparator());
        escritorAleatorio.close();
    }

    //Practica 2
    public PersonalInfo getPersonalInfo(int posicion) throws IOException {
        RandomAccessFile lectorEscritorAleatorio = new RandomAccessFile(new File(Constants.rutaDataPersonal), "rwd");
        lectorEscritorAleatorio.seek(posicion * this.longitudRegistros);
        String lineaLeida = lectorEscritorAleatorio.readLine();
        ArrayList<String> listaExtraida = new ArrayList<>();
        int valAnterior = 0;
        for (Pair<String,Integer> campo : campos) {
            listaExtraida.add(lineaLeida.substring(valAnterior, valAnterior + campo.getValue()).trim());
            valAnterior += campo.getValue();
        }
        return new PersonalInfo(listaExtraida.toArray(new String[9]));
    }

    public TreeSet<PersonalInfo> leer(Constants.Provincias tipoProvincia, TreeSet<PersonalInfo> conjuntoPersonal) throws IOException {
        TreeSet<PersonalInfo> conjuntoPersonalFiltrado = new TreeSet<>();
        for (PersonalInfo registro : conjuntoPersonal) {
            if (registro.getProvincia().equals(tipoProvincia)) {
                conjuntoPersonalFiltrado.add(registro);
            }
        }
        return conjuntoPersonalFiltrado;
    }
    public TreeSet<FinancieroInfo> leer(Constants.Irpf tipoIRPF, TreeSet<FinancieroInfo> conjuntoFinanciero) throws IOException {
        TreeSet<FinancieroInfo> conjuntoFinancieroFiltrado = new TreeSet<>();
        for (FinancieroInfo registro : conjuntoFinanciero) {
            if (registro.getIrpf().equals(tipoIRPF)) {
                conjuntoFinancieroFiltrado.add(registro);
            }
        }
        return conjuntoFinancieroFiltrado;
    }

    public TreeSet<PersonalInfo> extractPersonal() throws IOException {
        RandomAccessFile escritorAleatorio = new RandomAccessFile(this.file, "rwd");
        String lineaLeida = "";
        TreeSet<PersonalInfo> conjuntoPersonal = new TreeSet<>();
        while ((lineaLeida = escritorAleatorio.readLine()) != null) {
            ArrayList<String> listaExtraida = new ArrayList<>();
            int valAnterior = 0;
            for (Pair<String,Integer> campo : campos) {
                listaExtraida.add(lineaLeida.substring(valAnterior, valAnterior + campo.getValue()).trim());
                valAnterior += campo.getValue();
            }
            conjuntoPersonal.add(new PersonalInfo(listaExtraida.toArray(new String[9])));
        }
        escritorAleatorio.close();
        return conjuntoPersonal;
    }

    public TreeSet<FinancieroInfo> extractFinanciero() throws IOException {
        RandomAccessFile escritorAleatorio = new RandomAccessFile(this.file, "rwd");
        String lineaLeida = "";
        TreeSet<FinancieroInfo> conjuntoFinanciero = new TreeSet<>();
        while ((lineaLeida = escritorAleatorio.readLine()) != null) {
            ArrayList<String> listaExtraida = new ArrayList<>();
            int valAnterior = 0;
            for (Pair<String,Integer> campo : campos) {
                listaExtraida.add(lineaLeida.substring(valAnterior, valAnterior + campo.getValue()).trim());
                valAnterior += campo.getValue();
            }
            conjuntoFinanciero.add(new FinancieroInfo(listaExtraida.toArray(new String[8])));
        }
        escritorAleatorio.close();
        return conjuntoFinanciero;
    }
    //


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
        } catch (IOException e) {
            System.err.println("ERROR de E/S: " + e.getMessage());
        }
    }
}
