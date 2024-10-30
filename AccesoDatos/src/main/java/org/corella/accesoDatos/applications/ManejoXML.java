package org.corella.accesoDatos.applications;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class ManejoXML {

    private static final String INDENT_LEVEL = " ";

    private Document leerXML(String rutaFichero) {
        // DTD
        DocumentBuilderFactory dbf = ValidacionXML.validarXML();
        // XSD
        // DocumentBuilderFactory dbf = ValidacionXML.validarXML(new File("src/main/resources/XSDClientes.xsd"));
        Document documentoXML = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(new GestorEventos());
            documentoXML = db.parse(new File(rutaFichero));
            muestraNodo(documentoXML, System.out, 0);
        } catch (FileNotFoundException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void escribirXML() {
        try {
            //DTD
            //<clientes>
            //  <cliente DNI=123456789X>
            //      <nombre>Alejandro</nombre>
            //  </cliente>
            //<clientes>
            //
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.newDocument();
            documento.setXmlVersion("1.0");
            //<clientes>
            Element elementoClientes = documento.createElement("clientes");
            //Elemento raiz
            documento.appendChild(elementoClientes);
            //<cliente>
            Element elementoCliente = documento.createElement("cliente");
            elementoClientes.appendChild(elementoCliente);
            elementoCliente.setAttribute("DNI", "123456789X");
            //<nombre>
            Element elementoNombre = documento.createElement("nombre");
            elementoCliente.appendChild(elementoNombre);
            elementoNombre.appendChild(documento.createTextNode("Alejandro"));
            DOMSource domSource = new DOMSource(documento);
            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformador.setOutputProperty(OutputKeys.METHOD, "xml");
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            //DTD
            transformador.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "DTDClientes.dtd");
            transformador.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //Salida por pantalla
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformador.transform(domSource, sr);

            //Salida a FicheroOutXML.xml
            FileWriter fw = new FileWriter(new File("src/main/resources/FicheroOutXMLXSD.xml"));
            StreamResult sr2 = new StreamResult(fw);
            transformador.transform(domSource, sr2);
            fw.close();

        } catch (ParserConfigurationException | TransformerException | IOException e) {
            System.err.println(e.getMessage());
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

    public void run() {
        //leerXML("pom.xml");
        escribirXML();
        leerXML("src/main/resources/FicheroOutXMLDTD.xml");
    }

}
