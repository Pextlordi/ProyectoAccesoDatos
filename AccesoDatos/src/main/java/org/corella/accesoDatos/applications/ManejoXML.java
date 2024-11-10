package org.corella.accesoDatos.applications;

import jdk.jshell.execution.LocalExecutionControl;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.IntStream;

public class ManejoXML {

    private static final String INDENT_LEVEL = " ";
    private static final String amarillo = "\u001B[33m";
    private static final String cyan = "\u001B[36m";
    private static final String rojo = "\u001B[31m";
    private static final String verde = "\u001B[32m";
    private static final String reset = "\u001B[0m";

    private Document leerXMLDTD(String rutaFichero) {
        System.out.println("Lectura y validación DTD: ");
        //DTD
        DocumentBuilderFactory dbf = ValidacionXML.validarXML();
        dbf.setNamespaceAware(true);
        Document documentoXML = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new GestorEventos());
            documentoXML = db.parse(new File(rutaFichero));
            //muestraNodo(documentoXML, System.out, 0);
        } catch (FileNotFoundException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Lectura: OK");
        return documentoXML;
    }
    private Document leerXMLXSD(String rutaFichero) {
        System.out.println("Lectura y validación XSD: ");
        //XSD
        DocumentBuilderFactory dbf = ValidacionXML.validarXML(new File("src/main/resources/reedSchema.xsd"));
        dbf.setNamespaceAware(true);
        Document documentoXML = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new GestorEventos());
            documentoXML = db.parse(new File(rutaFichero));
            //muestraNodo(documentoXML, System.out, 0);
        } catch (FileNotFoundException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Lectura: OK");
        return documentoXML;
    }

    private static void muestraNodo(Node nodo, PrintStream printStream, int nivel) {
        if (nodo.getNodeType() == Node.TEXT_NODE && nodo.getNodeValue().trim().isEmpty()) {
            return;
        }
        for (int i = 0; i < nivel; i++) {
            printStream.print(INDENT_LEVEL);
        }
        switch(nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                Document doc = (Document) nodo;
                printStream.println("Documento DOM, version: " + doc.getXmlVersion() + ", codificacion: " + doc.getXmlEncoding());
                break;
            case Node.ELEMENT_NODE:
                printStream.print("<" + nodo.getNodeName());
                NamedNodeMap listaAtributos = nodo.getAttributes();
                for (int i = 0; i < listaAtributos.getLength(); i++) {
                    Node atributo = listaAtributos.item(i);
                    printStream.print(" @" + atributo.getNodeName() + " [" + atributo.getNodeValue() + "]");
                }
                printStream.println(">");
                break;
            case Node.TEXT_NODE:
                printStream.println(nodo.getNodeName() + " [" + nodo.getNodeValue() + "]");
                break;
            default:
                printStream.println("(nodo de tipo: " + nodo.getNodeType() + ")");
                break;
        }
        NodeList childNodes = nodo.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            muestraNodo(childNodes.item(i), printStream, nivel+1);
        }
    }

    class GestorEventos extends DefaultHandler {
        @Override
        public void error(SAXParseException e) throws SAXException {
            System.err.println("Error recuperable: " + e.getMessage());
            throw e;
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            System.err.println("Error no recuperable: " + e.getMessage());
            throw e;
        }

        @Override
        public void warning(SAXParseException e) throws SAXException {
            System.err.println("Aviso: " + e.getMessage());
            throw e;
        }
    }

    private void contarCursos(Document documento) {
        NodeList listaRepetida = documento.getElementsByTagName("title");
        TreeSet<String> listaUnica = new TreeSet<>();
        for (int i = 0; i < listaRepetida.getLength(); i++) {
            if (listaRepetida.item(i).getChildNodes().item(0) != null) {
                listaUnica.add(listaRepetida.item(i).getChildNodes().item(0).getNodeValue());
            }

        }
        System.out.println("Cursos disponibles: " + listaUnica.size());
    }

    private void getInstructores(Document documento) {
        NodeList listaRepetida = documento.getElementsByTagName("instructor");
        TreeSet<String> listaUnica = new TreeSet<>();
        for (int i = 0; i < listaRepetida.getLength(); i++) {
            if (listaRepetida.item(i).getChildNodes().item(0) != null) {
                listaUnica.add(listaRepetida.item(i).getChildNodes().item(0).getNodeValue());
            }
        }
        System.out.println(rojo + "Instructores: ");
        for (String instructor : listaUnica) {
            System.out.println("- " + instructor);
        }
        System.out.println(reset);
    }

    private void cursosImpartidos(Document documento) {
        NodeList listaRepetida = documento.getElementsByTagName("title");
        HashMap<String, Integer> listaUnica = new HashMap<>();
        for (int i = 0; i < listaRepetida.getLength(); i++) {
            Node nodo = listaRepetida.item(i).getChildNodes().item(0);
            if (nodo != null) {
                if(listaUnica.containsKey(nodo.getNodeValue())) {
                    listaUnica.put(nodo.getNodeValue(), listaUnica.get(nodo.getNodeValue()) + 1);
                } else {
                    listaUnica.put(nodo.getNodeValue(), 1);
                }
            }
        }
        System.out.println(verde + "Cursos: ");
        for (Map.Entry<String, Integer> entrada : listaUnica.entrySet()) {
            System.out.print("- " + entrada.getKey() + " se ha impartido " + entrada.getValue());
            if (entrada.getValue() == 1) {
                System.out.println(" vez");
            } else {
                System.out.println(" veces");
            }
        }
        System.out.println(reset);
    }

    private void maxminDuracion(Document documento) {
        NodeList listaCursos = documento.getElementsByTagName("course");
        //Lista de nodos por si hay varios cursos máximo o mínimos
        List<Node> cursosMax = new ArrayList<>();
        List<Node> cursosMin = new ArrayList<>();
        cursosMin.add(listaCursos.item(0));
        cursosMax.add(listaCursos.item(0));
        int tiempoMin = extraerTiempoTotal(listaCursos.item(0));
        int tiempoMax = extraerTiempoTotal(listaCursos.item(0));
        for (int i = 1; i < listaCursos.getLength(); i++) {
            NodeList horario = listaCursos.item(i).getChildNodes().item(8).getChildNodes();
            if (horario.item(0).getChildNodes().item(0) != null || horario.item(1).getChildNodes().item(0) != null) {
                int tiempoCheck = extraerTiempoTotal(listaCursos.item(i));
                //Comprobar si es menor que el mínimo o igual que el mínimo

                if (tiempoCheck < tiempoMin) {
                    cursosMin.clear();
                    cursosMin.add(listaCursos.item(i));
                    tiempoMin = extraerTiempoTotal(cursosMin.get(0));
                } else if (tiempoCheck == tiempoMin) {
                    cursosMin.add(listaCursos.item(i));
                }
                //Comprobar si es mayor que el máximo o igual que el mínimo
                if (tiempoCheck > tiempoMax) {
                    cursosMax.clear();
                    cursosMax.add(listaCursos.item(i));
                    tiempoMax = extraerTiempoTotal(cursosMax.get(0));
                } else if (tiempoCheck == tiempoMax) {
                    cursosMax.add(listaCursos.item(i));
                }
            }
        }

        System.out.println(amarillo + "Cursos más largos (Duración semanal de " + tiempoMax + " minutos):");
        System.out.println("--------------------------");
        for (Node curso : cursosMax) {
            muestraNodo(curso, System.out, 0);
            System.out.println("--------------------------");
        }
        System.out.println(reset);

        System.out.println(cyan + "Cursos más cortos (Duración semanal de " + tiempoMin + " minutos):");
        System.out.println("//////////////////////////");
        for (Node curso : cursosMin) {
            muestraNodo(curso, System.out, 0);
            System.out.println("//////////////////////////");
        }
        System.out.println(reset);
    }

    private int extraerTiempoTotal (Node nodo) {
        NodeList horario = nodo.getChildNodes().item(8).getChildNodes();
        String stringCompienzo = horario.item(0).getChildNodes().item(0).getNodeValue();
        String stringFinalizacion = horario.item(1).getChildNodes().item(0).getNodeValue();
        LocalTime horaComienzo = LocalTime.parse(stringCompienzo.substring(0, 5));
        LocalTime horaFinalizacion = LocalTime.parse(stringFinalizacion);
        int diasSemanales = nodo.getChildNodes().item(7).getChildNodes().item(0).getNodeValue().split("-").length;
        return (int)(Duration.between(horaComienzo, horaFinalizacion).toMinutes()) * diasSemanales;
    }

    public void run() {
        leerXMLDTD("src/main/resources/reed.xml");
        Document documento = leerXMLXSD("src/main/resources/reed.xml");
        //Guardamos uno de los documentos validados
        contarCursos(documento);
        getInstructores(documento);
        cursosImpartidos(documento);
        maxminDuracion(documento);
    }




}
