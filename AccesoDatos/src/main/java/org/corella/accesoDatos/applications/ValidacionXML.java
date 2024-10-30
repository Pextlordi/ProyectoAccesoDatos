package org.corella.accesoDatos.applications;

import org.corella.accesoDatos.utilsAccesoFichero.Constants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ValidacionXML {

    public static DocumentBuilderFactory validarXML() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setValidating(true);
        return dbf;
    }

    public static DocumentBuilderFactory validarXML(File schema) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        try {
            dbf.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schema));

        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return dbf;
    }

    public void run() {

    }

}
