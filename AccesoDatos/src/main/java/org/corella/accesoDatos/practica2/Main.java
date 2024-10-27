package org.corella.accesoDatos.practica2;

import javafx.util.Pair;
import org.corella.accesoDatos.applications.FicheroAccesoAleatorio;
import org.corella.accesoDatos.practica2.entities.FinancieroInfo;
import org.corella.accesoDatos.practica2.entities.PersonalInfo;
import org.corella.accesoDatos.practica2.utils.Constants;
import org.corella.accesoDatos.practica2.utils.IrpfRango;
import org.corella.accesoDatos.practica2.utils.ProvinciaRango;
import org.corella.accesoDatos.utilsAccesoFichero.Escritor;
import org.corella.accesoDatos.utilsAccesoFichero.Lector;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            //Lectura de csv y guardado
            TreeSet<PersonalInfo> infoPersonal = leerCSVPersonal();
            TreeSet<FinancieroInfo> infoFinanciero = leerCSVFinanciero();
            guardarInfoPersonal(infoPersonal);
            guardarInfoFinanciero(infoFinanciero);
            //Imprimir información guardada
            System.out.println("-----Información Personal-----");
            imprimirPersonal(infoPersonal);
            System.out.println("-----Información Financiera------");
            imprimirFinanciero(infoFinanciero);
            //Métodos de lectura
            System.out.println("-----Leemos posición 3-----");
            leerCliente(3);
            System.out.println("-----Leemos todos los clientes con Irpf 5-----");
            leerCliente(Constants.Irpf.IRPF_5);
            System.out.println("-----Leemos todos los clientes que vienen de Madrid-----");
            leerCliente(Constants.Provincias.MADRID);
            //Creación de informes
            formarInformeSaldo();
            formarInformeMorosos();
            formarInformeProvincia();
            System.out.println("-----INFO: Informes Creados en /src/main/java/org.corella.accesoDatos/practica/out/informes-----");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //Imprimir conjuntos
    private static void imprimirPersonal(TreeSet<PersonalInfo> infoFinanciero) {
        for (PersonalInfo persona : infoFinanciero) {
            System.out.println(persona);
        }
    }

    private static void imprimirFinanciero(TreeSet<FinancieroInfo> infoPersonal) {
        for (FinancieroInfo persona : infoPersonal) {
            System.out.println(persona);
        }
    }

    //Leer CSV de datos
    private static TreeSet<PersonalInfo> leerCSVPersonal() throws IOException{
        TreeSet<PersonalInfo> conjuntoPersonal = new TreeSet<>();
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(Constants.rutaCSVPersonal));
        lectorLineas.readLine();
        String lineaLeida;
        while ((lineaLeida = lectorLineas.readLine()) != null) {
            conjuntoPersonal.add(new PersonalInfo(lineaLeida.split(",")));
        }
        return conjuntoPersonal;
    }

    private static TreeSet<FinancieroInfo> leerCSVFinanciero() throws IOException {
        TreeSet<FinancieroInfo> conjuntoFinanciero = new TreeSet<>();
        BufferedReader lectorLineas = new Lector().lectorLineas(new File(Constants.rutaCSVFinanciero));
        lectorLineas.readLine();
        String lineaLeida;
        while ((lineaLeida = lectorLineas.readLine()) != null) {
            conjuntoFinanciero.add(new FinancieroInfo(lineaLeida.split(",")));
        }
        return conjuntoFinanciero;
    }

    //Guardado de datos
    private static void guardarInfoPersonal(TreeSet<PersonalInfo> infoPersonal) throws IOException {
        vaciarFichero(Constants.rutaDataPersonal);
        ProvinciaRango tablaProvincia = new ProvinciaRango();
        List<Pair<String, Integer>> campos = PersonalInfo.getCamposPersonal();
        FicheroAccesoAleatorio lectorEscritor = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, campos);
        Constants.Provincias provinciaActiva = null;
        int contador = 0;
        for (PersonalInfo personaEscribir : infoPersonal) {
            contador++;
            Map<String, String> registro = mapearCamposPersonal(personaEscribir, campos);
            lectorEscritor.insertar(registro);
            if (provinciaActiva == null) {
                provinciaActiva = personaEscribir.getProvincia();
            } else if (provinciaActiva != personaEscribir.getProvincia()) {
                tablaProvincia.setProvincia(provinciaActiva, contador-1);
                provinciaActiva = personaEscribir.getProvincia();
            }
        }

        if (provinciaActiva != null) {
            tablaProvincia.setProvincia(provinciaActiva, (int) lectorEscritor.getNumRegistros());
        }
        tablaProvincia.flushProvincia();
    }

    private static Map<String, String> mapearCamposPersonal(PersonalInfo personaEscribir, List<Pair<String, Integer>> campos) {
        Map<String, String> registro = new HashMap<>();
        registro.put(campos.get(0).getKey(), personaEscribir.getNombre());
        registro.put(campos.get(1).getKey(), personaEscribir.getApellido1());
        registro.put(campos.get(2).getKey(), personaEscribir.getApellido2());
        registro.put(campos.get(3).getKey(), personaEscribir.getDirResidencia());
        registro.put(campos.get(4).getKey(), personaEscribir.getDirFiscal());
        registro.put(campos.get(5).getKey(), String.valueOf(personaEscribir.getProvincia()));
        registro.put(campos.get(6).getKey(), String.valueOf(personaEscribir.getCodPostal()));
        registro.put(campos.get(7).getKey(), personaEscribir.getDni());
        registro.put(campos.get(8).getKey(), personaEscribir.getNumSS());
        return registro;
    }

    private static void guardarInfoFinanciero(TreeSet<FinancieroInfo> infoFiscal) throws IOException {
        vaciarFichero(Constants.rutaDataFinanciero);
        IrpfRango tablaIRPF = new IrpfRango();
        List<Pair<String, Integer>> campos = FinancieroInfo.getCamposFinanciero();
        FicheroAccesoAleatorio lectorEscritor = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, campos);
        Constants.Irpf irpfActiva = null;
        int contador = 0;
        for (FinancieroInfo personaEscribir : infoFiscal) {
            contador++;
            Map<String, String> registro = mapearCamposFinanciero(personaEscribir, campos);
            lectorEscritor.insertar(registro);
            //Escritor de rangos en Irpf.txt
            if (irpfActiva == null) {
                irpfActiva = personaEscribir.getIrpf();
            } else if (irpfActiva != personaEscribir.getIrpf()) {
                tablaIRPF.setIrpf(irpfActiva, contador-1);
                irpfActiva = personaEscribir.getIrpf();
            }
        }
        //Último rango
        if (irpfActiva != null) {
            tablaIRPF.setIrpf(irpfActiva, (int) lectorEscritor.getNumRegistros());
        }
        tablaIRPF.flushIrpf();
    }

    private static Map<String, String> mapearCamposFinanciero(FinancieroInfo personaEscribir, List<Pair<String, Integer>> campos) {
        Map<String, String> registro = new HashMap<>();
        registro.put(campos.get(0).getKey(), String.valueOf(personaEscribir.getId()));
        registro.put(campos.get(1).getKey(), personaEscribir.getDni());
        registro.put(campos.get(2).getKey(), String.valueOf(personaEscribir.getBalance()));
        registro.put(campos.get(3).getKey(), String.valueOf(personaEscribir.getNumCuenta()));
        registro.put(campos.get(4).getKey(), String.valueOf(personaEscribir.getIrpf()));
        registro.put(campos.get(5).getKey(), String.valueOf(personaEscribir.getAportacionesONG()));
        registro.put(campos.get(6).getKey(), String.valueOf(personaEscribir.getAportacionesFondo()));
        //No escribimos nada si no hay tipo de fondo
        if (personaEscribir.getTipoFondo() == null) {
            registro.put(campos.get(7).getKey(), "");
        } else {
            registro.put(campos.get(7).getKey(), String.valueOf(personaEscribir.getTipoFondo()));
        }
        return registro;
    }

    private static void vaciarFichero(String ruta) throws IOException {
        RandomAccessFile borrador = new RandomAccessFile(new File(ruta), "rw");
        borrador.setLength(0);
        borrador.close();
    }

    //Lectores de clientes
    private static PersonalInfo getCliente(int posicion) throws IOException {
        TreeSet<FinancieroInfo> listaFinancieroClientes = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero()).extractFinanciero();
        PersonalInfo registroPersonalLeido = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, PersonalInfo.getCamposPersonal()).getPersonalInfo(posicion);
        for (FinancieroInfo registroFinanciero : listaFinancieroClientes) {
            if (registroFinanciero.getDni().equals(registroPersonalLeido.getDni())) {
                return registroPersonalLeido;
            }
        }
        return null;
    }
    private static void leerCliente(int posicion) throws IOException{
        PersonalInfo registroPersona = getCliente(posicion);
        System.out.println(registroPersona + "| "+ registroPersona.getFinanciero());
    }

    private static void leerCliente(Constants.Provincias tipoProvincia) throws IOException {
        FicheroAccesoAleatorio lectorEscritor = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, PersonalInfo.getCamposPersonal());
        TreeSet<PersonalInfo> listaPersonalFiltrado = lectorEscritor.leer(tipoProvincia, lectorEscritor.extractPersonal());
        TreeSet<FinancieroInfo> listaFinancieroClientes = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero()).extractFinanciero();
        for (PersonalInfo registroPersonal : listaPersonalFiltrado) {
            for (FinancieroInfo registroFinanciero : listaFinancieroClientes) {
                if (registroFinanciero.getDni().equals(registroPersonal.getDni())) {
                    System.out.println(registroPersonal + "| " + registroFinanciero);
                    break;
                }
            }
        }
    }

    private static void leerCliente(Constants.Irpf tipoIRPF) throws IOException {
        FicheroAccesoAleatorio lectorEscritor = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero());
        TreeSet<PersonalInfo> listaPersonalClientes = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, PersonalInfo.getCamposPersonal()).extractPersonal();
        TreeSet<FinancieroInfo> listaFinancieroClientes = lectorEscritor.leer(tipoIRPF, lectorEscritor.extractFinanciero());
        for (PersonalInfo registroPersonal : listaPersonalClientes) {
            for (FinancieroInfo registroFinanciero : listaFinancieroClientes) {
                if (registroFinanciero.getDni().equals(registroPersonal.getDni())) {
                    System.out.println(registroPersonal + "| " + registroFinanciero);
                }
            }
        }
    }

    //Creación de Informes
    private static void formarInformeSaldo() throws IOException {
        TreeSet<FinancieroInfo> listaFinancieroClentes = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero()).extractFinanciero();
        double saldoTotal = 0.0;
        for (FinancieroInfo registroFinanciero : listaFinancieroClentes) {
            saldoTotal += registroFinanciero.getBalance();
        }
        BufferedWriter escritorLineas = new Escritor().escritorLineas(new File(Constants.rutaInformeSaldo));
        escritorLineas.write("El saldo promedio de los clientes es "+ String.format("%.2f", saldoTotal/listaFinancieroClentes.size()) + " euros");
        escritorLineas.close();
    }

    private static void formarInformeMorosos() throws IOException {
        TreeSet<PersonalInfo> listaPersonalClientes = new FicheroAccesoAleatorio(Constants.rutaDataPersonal, PersonalInfo.getCamposPersonal()).extractPersonal();
        TreeSet<FinancieroInfo> listaFinancieroClientes = new FicheroAccesoAleatorio(Constants.rutaDataFinanciero, FinancieroInfo.getCamposFinanciero()).extractFinanciero();
        int numMorosos = 0;
        ArrayList<FinancieroInfo> listaFinancieroMorosos = new ArrayList<>();
        for (FinancieroInfo registroFinanciero : listaFinancieroClientes) {
            if (registroFinanciero.getBalance() < 0) {
                listaFinancieroMorosos.add(registroFinanciero);
                numMorosos++;
            }
        }
        BufferedWriter escritorLineas = new Escritor().escritorLineas(new File(Constants.rutaInformeMorosos));
        escritorLineas.write("En total hay " + numMorosos + " morosos. Son los siguientes:");
        for (FinancieroInfo registroFinanciero : listaFinancieroMorosos) {
            for (PersonalInfo registroPersonal : listaPersonalClientes) {
                if (registroPersonal.getDni().equals(registroFinanciero.getDni())) {
                    escritorLineas.newLine();
                    escritorLineas.write(registroPersonal.getInfoAnonimizado() + "| " + registroFinanciero);
                }
            }
        }
        escritorLineas.close();
    }

    private static void formarInformeProvincia() throws IOException {
        ProvinciaRango tablaProvincias = new ProvinciaRango();
        BufferedWriter escritorLineas = new Escritor().escritorLineas(new File(Constants.rutaInformeProvincia));
        escritorLineas.write("Saldo por provincia:");
        Map<Constants.Provincias, Map.Entry<Integer,Integer>> mapaProvincias = tablaProvincias.getConjuntoProvincia();
        for (Constants.Provincias provinciaActiva : mapaProvincias.keySet()) {
            ArrayList<String> conjuntoClientes = new ArrayList<>();
            double saldoProvincia = 0.0;
            for (int i = mapaProvincias.get(provinciaActiva).getKey() - 1; i < mapaProvincias.get(provinciaActiva).getValue(); i++) {
                PersonalInfo registroPersonal = getCliente(i);
                FinancieroInfo registroFinanciero = registroPersonal.getFinanciero();
                saldoProvincia += registroFinanciero.getBalance();
                conjuntoClientes.add("\t" + registroPersonal.getInfoAnonimizado() + "| " + registroFinanciero);
            }
            escritorLineas.newLine();
            escritorLineas.write(provinciaActiva.toString() + " tiene un saldo total de "+ saldoProvincia + " euros con los clientes:");
            for (String cliente : conjuntoClientes) {
                escritorLineas.newLine();
                escritorLineas.write(cliente);
            }
        }
        escritorLineas.close();
    }
}
