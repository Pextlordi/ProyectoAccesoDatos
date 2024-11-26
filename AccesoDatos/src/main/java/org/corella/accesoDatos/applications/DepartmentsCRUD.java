package org.corella.accesoDatos.applications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentsCRUD {
    private final Connection conexion;
    private final String insert;
    private final String readName;
    private final String readId;
    private final String updateName;
    private final String updateId;
    private final String deleteName;
    private final String deleteId;
    public DepartmentsCRUD(Connection conexion) {
        this.conexion = conexion;
        insert = "INSERT INTO departments(`dept_no`, `dept_name`) VALUES (?,?) ";
        readName = "SELECT * FROM departments WHERE dept_name= ? ";
        readId = "SELECT * FROM departments WHERE dept_no= ? ";
        updateName = "UPDATE departments SET dept_name = ? WHERE dept_no = ? ";
        updateId = "UPDATE departments SET dept_no = ? WHERE dept_name = ?";
        deleteName = "DELETE FROM departments WHERE dept_name = ? ";
        deleteId = "DELETE FROM departments WHERE dept_no = ? ";
    }

    public void insertar(String dept_no, String dept_name) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(insert);
        sentenciaPreparada.setString(1, dept_no);
        sentenciaPreparada.setString(2, dept_name);
        conexion.setAutoCommit(false);
        int output = sentenciaPreparada.executeUpdate();
        System.out.println("Salida insert: " + output);
        conexion.commit();
    }

    public void actualizarNombre(String dept_no, String dept_name) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(updateName);
        sentenciaPreparada.setString(1, dept_name);
        sentenciaPreparada.setString(2, dept_no);
        int output = sentenciaPreparada.executeUpdate();
        System.out.println("Salida update: " + output);
    }

    public void actualizarDepartamento(String dept_name, String dept_no) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(updateId);
        sentenciaPreparada.setString(1, dept_no);
        sentenciaPreparada.setString(2, dept_name);
        int output = sentenciaPreparada.executeUpdate();
        System.out.println("Salida update: " + output);
    }

    public void eliminarNombre(String dept_name) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(deleteName);
        sentenciaPreparada.setString(1, dept_name);
        int output = sentenciaPreparada.executeUpdate();
        System.out.println("Salida eliminar: " + output);
    }

    public void eliminarDepartamento(String dept_no) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(deleteId);
        sentenciaPreparada.setString(1, dept_no);
        int output = sentenciaPreparada.executeUpdate();
        System.out.println("Salida eliminar: " + output);
    }

    public void getDepartamentoNombre(String dept_name) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(readName);
        sentenciaPreparada.setString(1, dept_name);
        ResultSet resultSet = sentenciaPreparada.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("dept_no") + " " + resultSet.getString("dept_name"));
        }
    }

    public void getDepartamentoId(String dept_no) throws SQLException {
        PreparedStatement sentenciaPreparada = conexion.prepareStatement(readId);
        sentenciaPreparada.setString(1, dept_no);
        ResultSet resultSet = sentenciaPreparada.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("dept_no") + " " + resultSet.getString("dept_name"));
        }
    }
}
