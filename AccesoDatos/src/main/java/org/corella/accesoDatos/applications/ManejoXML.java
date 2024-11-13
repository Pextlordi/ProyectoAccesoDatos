package org.corella.accesoDatos.applications;

import org.exist.xmldb.LocalCollectionManagementService;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import javax.xml.transform.OutputKeys;
import org.exist.xmldb.EXistResource;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class ManejoXML {

    private static final String INDENT_LEVEL = " ";

    private Document leerXML(String rutaFichero) {
        //DTD
        //DocumentBuilderFactory dbf = ValidacionXML.validarXML();
        //XSD
        DocumentBuilderFactory dbf = ValidacionXML.validarXML(new File("src/main/resources/XSDClientes.xsd"));
        dbf.setNamespaceAware(true);
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
            //XSD
            //elementoClientes.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            //elementoClientes.setAttribute("xsi:schemaLocation", "XSDClientes.xsd");
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
            FileWriter fw = new FileWriter(new File("src/main/resources/FicheroOutXML.xml"));
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

    /*private static Collection getCollection() throws Exception {
        Database databaseDriver;
        Collection collection;
        databaseDriver = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
        collection = DatabaseManager.getCollection("http://localhost:8080/db/apps/demo/data/addresses/", "admin", "1234");

        return collection;
    }
    */
    private static Collection obtenerColeccion() throws Exception {
        String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/apps/demo/data/addresses/";

        /**
         * args[0] Should be the name of the collection to access
         * args[1] Should be the name of the resource to read from the collection
         */

            final String driver = "org.exist.xmldb.DatabaseImpl";

            // initialize database driver
            Class cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);

            Collection col = null;
            XMLResource res = null;
            try {
                // get the collection
                col = DatabaseManager.getCollection(URI, "admin", "admin");
                col.setProperty(OutputKeys.INDENT, "no");
                res = (XMLResource)col.getResource("0ff8612a-b998-4677-84a3-73e9ef84ba5f.xml");

                if(res == null) {
                    System.out.println("document not found!");
                } else {
                    System.out.println(res.getContent());
                }
            } finally {
                //dont forget to clean up!

                if(res != null) {
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }

                if(col != null) {
                    try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        return col;
    }
    private static Collection crearColeccion(String URIentrada, String nombre) throws Exception {
        String URI = URIentrada;

        /**
         * args[0] Should be the name of the collection to access
         * args[1] Should be the name of the resource to read from the collection
         */

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI, "admin", "admin");
            col.setProperty(OutputKeys.INDENT, "no");
            CollectionManagementService servicio = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
            Collection coleccionCreada = servicio.createCollection(nombre);
            if (coleccionCreada != null) {
                System.out.println("La coleccion " + nombre + " ha sido creada");
            } else {
                System.out.println("La coleccion " + nombre + " no se pudo crear");
            }
        } finally {
            //dont forget to clean up!

            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return col;
    }

    private static Collection borrarColeccion(String URIentrada, String nombre) throws Exception {
        String URI = URIentrada;

        /**
         * args[0] Should be the name of the collection to access
         * args[1] Should be the name of the resource to read from the collection
         */

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI, "admin", "admin");
            col.setProperty(OutputKeys.INDENT, "no");
            CollectionManagementService servicio = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
            servicio.removeCollection(nombre);
            System.out.println("La coleccion " + nombre +" se ha intentado borrar");
        } finally {
            //dont forget to clean up!

            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return col;
    }

    private static Collection crearRecurso(String URIentrada, String nombre) throws Exception {
        String URI = URIentrada;

        /**
         * args[0] Should be the name of the collection to access
         * args[1] Should be the name of the resource to read from the collection
         */

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource reswrite = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI, "admin", "admin");
            reswrite = (XMLResource) col.createResource(nombre, "XMLResource");
            reswrite.setContent("<root><elementoPrueba>contenido</elementoPrueba></root>");
            col.storeResource(reswrite);
            if (reswrite != null) {
                System.out.println("El recurso " + nombre + " ha sido creado");
            } else {
                System.out.println("El recurso " + nombre + " no se pudo crear");
            }

        } finally {
            //dont forget to clean up!

            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return col;
    }

    private static Collection borrarRecurso(String URIentrada, String nombre) throws Exception {
        String URI = URIentrada;

        /**
         * args[0] Should be the name of the collection to access
         * args[1] Should be the name of the resource to read from the collection
         */

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource reswrite = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI, "admin", "admin");
            reswrite = (XMLResource) col.createResource(nombre, "XMLResource");
            //reswrite.setContent("<root><elementoPrueba>contenido</elementoPrueba></root>");
            col.removeResource(reswrite);
            System.out.println("El recurso " + nombre + " se ha intentado borrar");
        } finally {
            //dont forget to clean up!

            if (res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if (col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
        return col;
    }
    public void run() {
        //leerXML("pom.xml");
        //escribirXML();
        //leerXML("src/main/resources/FicheroOutXML.xml");
        try {
            //Collection collection = getCollection();
            //Collection collection = obtenerColeccion();

            //Crear y borran coleccion.
            //Crear (con contenido) un recurso y borrar un recurso.
            crearColeccion("xmldb:exist://localhost:8080/exist/xmlrpc/db", "MiColeccion");
            crearRecurso("xmldb:exist://localhost:8080/exist/xmlrpc/db/MiColeccion", "RecursoCreado.xml");
            //borrarColeccion("xmldb:exist://localhost:8080/exist/xmlrpc/db", "MiColeccion");
            //borrarRecurso("xmldb:exist://localhost:8080/exist/xmlrpc/db/MiColeccion", "RecursoCreado.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
