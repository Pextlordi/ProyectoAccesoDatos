package org.corella.accesoDatos.applications;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

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
    public void run() throws IOException {
        escribirJson();
    }
}
