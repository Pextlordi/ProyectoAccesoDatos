package org.corella.accesoDatos.applications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.corella.accesoDatos.entities.AlumnoJSON;

import java.io.*;
import java.util.ArrayList;

public class ManejoJSON {
    private void escribirJson() throws IOException {
        JSONObject objetoJSON = new JSONObject();
        objetoJSON.put("Nombre", "Petteri");
        objetoJSON.put("Edad", 23);
        JSONArray asignaturasMatriculadas = new JSONArray();
        asignaturasMatriculadas.put(0,"Acceso_a_Datos");
        asignaturasMatriculadas.put(1, "EEIE");
        objetoJSON.put("Asignaturas_Matriculadas", asignaturasMatriculadas);
        JSONArray notasAsignaturas = new JSONArray();
        JSONObject asignaturaNotas = new JSONObject();
        asignaturaNotas.put("Base de Datos", 10);
        asignaturaNotas.put("Programación", 10);
        notasAsignaturas.put(0, asignaturaNotas);
        objetoJSON.put("Asignaturas_Notas", notasAsignaturas);
        System.out.println(objetoJSON.toString());
    }

    private static void serializableOrgJSON(){
        Writer demoJson = new StringWriter();
        JSONWriter writerJson = new JSONWriter(demoJson);
        writerJson.object();
        writerJson.key("Nombre").value("Petteri");
        writerJson.key("Edad").value(23);
        writerJson.key("Titula").value(true);
        writerJson.key("AsignaturasSuperadas").array()
                .value("Base de datos")
                .value("Programacion")
                .value("Empresa e iniciativa Emprendedora");
        writerJson.endArray();
        writerJson.endObject();
        System.out.println(demoJson.toString());
    }

    private static void deserializableOrgJSON() {}

    private static void writeJacksonJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File fichero = new File("src/main/resources/FicheroOutAlumnos.json");
        ArrayList<String> aSup = new ArrayList<>();
        AlumnoJSON unai = new AlumnoJSON("Unai",aSup,20,false);
        mapper.writeValue(fichero, unai);
        AlumnoJSON miguel = new AlumnoJSON("Miguel",aSup,21, true);
        mapper.writeValue(fichero, miguel);
    }

    private static void readJacksonJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AlumnoJSON alumno = mapper.readValue(new File("src/main/resources/FicheroOutAlumnos.json"),AlumnoJSON.class);
        System.out.println(alumno);
    }
    public void run() throws IOException {
        //escribirJson();
        //serializableOrgJSON();
        //deserializableOrgJSON();
        writeJacksonJSON();
        readJacksonJSON();
    }
}
