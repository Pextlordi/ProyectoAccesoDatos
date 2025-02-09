package org.corella.accesoDatos.applications;


import org.corella.accesoDatos.entities.Empleado;
import org.corella.accesoDatos.entities.JugBaloncestoBD;
import org.corella.accesoDatos.entities.Salario;

import java.sql.*;
import java.util.ArrayList;

public class ConectorMySQL {
    //private static final String driver = "com.mysql.cj.jdbc.Driver";
    private String hostname = "localhost";
    private String port = "3306";
    private String database = "employees";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
    private static final String username = "root";
    private static final String password = "admin";

    public Connection conectarMySQL() throws SQLException {
        Connection conexion = null;
        conexion = DriverManager.getConnection(url, username, password);
        return conexion;
    }

    public String consultarJugadores(Connection conexion) throws SQLException {
        Statement sentencia = conexion.createStatement();
        ResultSet resultadoConsulta = sentencia.executeQuery("SELECT * FROM Jugadores");
        String nombreJugador = null;
        while (resultadoConsulta.next()) {
            nombreJugador = resultadoConsulta.getString("Nombre");
        }
        return nombreJugador;
    }

    public String consultarJugadoresPrepared(Connection conexion) throws SQLException {
        String nombreJugador = null;
        PreparedStatement sentencia = conexion.prepareStatement("SELECT * FROM Jugadores where Posicion = ?");
        sentencia.setString(1, "Base");
        ResultSet resultadoConsulta = sentencia.executeQuery();
        while (resultadoConsulta.next()) {
            nombreJugador = resultadoConsulta.getString("Nombre");
        }
        return nombreJugador;
    }

    public void anadirJugador(Connection conexion) throws SQLException {
        PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO `Jugadores`(`Nombre`, `Posicion`, `PuntosPartido`) VALUES (?,?,?)");
        sentencia.setString(1, "Nico");
        sentencia.setString(2, "Base");
        sentencia.setDouble(3, 0.2);
        conexion.setAutoCommit(false);
        int output = sentencia.executeUpdate();
        System.out.println(output);
        conexion.commit();

    }

    public void empleadosPorDept(Connection conexion) throws SQLException {
        Statement sentencia = conexion.createStatement();
        ResultSet resultadoConsulta = sentencia.executeQuery("SELECT dept.dept_name as Departamento, count(dept_emp.emp_no) as Empleados FROM departments as dept join dept_emp using(dept_no) group by dept.dept_name");
        while (resultadoConsulta.next()) {
            System.out.println(resultadoConsulta.getString("Departamento") + ": " + resultadoConsulta.getInt("Empleados"));
        }
    }

    public void salariosempleados(Connection conexion) throws SQLException {
        ArrayList<Empleado> listaEmpleado = new ArrayList<>();
        Statement sentencia = conexion.createStatement();
        ResultSet resultadoConsulta = sentencia.executeQuery("SELECT emp.*, sal.* FROM employees as emp join salaries as sal using(emp_no)");
        resultadoConsulta.next();
        Empleado empleadoActivo = new Empleado(resultadoConsulta.getString(1),resultadoConsulta.getString(2),resultadoConsulta.getString(3),resultadoConsulta.getString(4),resultadoConsulta.getString(5),resultadoConsulta.getString(6));
        empleadoActivo.agregarSalario(new Salario(resultadoConsulta.getString(8), resultadoConsulta.getString(9), resultadoConsulta.getString(10)));
        while (resultadoConsulta.next()) {
            String emp_no = resultadoConsulta.getString(1);
            if (empleadoActivo.getNum().equals(emp_no)) {
                empleadoActivo.agregarSalario(new Salario(resultadoConsulta.getString(8), resultadoConsulta.getString(9), resultadoConsulta.getString(10)));
            } else {
                listaEmpleado.add(empleadoActivo);
                empleadoActivo = new Empleado(emp_no, resultadoConsulta.getString(2),resultadoConsulta.getString(3),resultadoConsulta.getString(4),resultadoConsulta.getString(5), resultadoConsulta.getString(6));
                empleadoActivo.agregarSalario(new Salario(resultadoConsulta.getString(8), resultadoConsulta.getString(9), resultadoConsulta.getString(10)));
            }
        }
        listaEmpleado.add(empleadoActivo);
        for (Empleado emp : listaEmpleado) {
            System.out.println(emp);
        }

    }

    private void callable(Connection conexion) throws SQLException {
            CallableStatement cs = conexion.prepareCall("{CALL SumaDeptAno(?,?,?)}");
        cs.setString(1, "Development");
        cs.setInt(2, 1990);
        cs.registerOutParameter(3, Types.DECIMAL);
        cs.execute();
        double res = cs.getDouble(3);
        System.out.println(res);
        cs.close(); //Se puede omitir usando un try catch
    }

    private void callableFunction(Connection conexion) throws SQLException {
        CallableStatement cs = conexion.prepareCall("{? = CALL SumaDeptAnoFunc(?,?)}");
        cs.registerOutParameter(1, Types.DECIMAL);
        cs.setString(2, "Development");
        cs.setInt(3, 1990);
        cs.execute();
        double res = cs.getDouble(1);
        System.out.println(res);
        cs.close(); //Se puede omitir usando un try catch
    }

    private void accesoFuncion(Connection conexion) throws SQLException {
        PreparedStatement sentencia = conexion.prepareStatement("SELECT SumaDeptAnoFunc(?,?)");
        sentencia.setString(1, "Development");
        sentencia.setInt(2, 1990);
        ResultSet resultadoConsulta = sentencia.executeQuery();
        while (resultadoConsulta.next()) {
            System.out.println(resultadoConsulta.getString(1));
        }
    }

    private void ejecutarFichero(Connection conexion) {
        ArrayList<JugBaloncestoBD> listaJugadores = new ArrayList<>();
        listaJugadores.add(new JugBaloncestoBD("Juan", "Base", 6.4));
        listaJugadores.add(new JugBaloncestoBD("Aitana", "Alero", 0.6));
        listaJugadores.add(new JugBaloncestoBD("Miguel", "Escolta", 3.0));
        try (PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO `Jugadores`(`Nombre`, `Posicion`, `PuntosPartido`) VALUES (?,?,?)")){
            conexion.setAutoCommit(false);
            for (JugBaloncestoBD jug : listaJugadores) {
                sentencia.setString(1, jug.getNombre());
                sentencia.setString(2, jug.getPosicion());
                sentencia.setDouble(3, jug.getPuntosPorPartido());
                sentencia.addBatch();
            }
            sentencia.executeBatch();
            conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Connection conexion = conectarMySQL();
            /*
            String jugador = consultarJugadores(conexion);
            String jugador2 = consultarJugadoresPrepared(conexion);
            anadirJugador(conexion);
            System.out.println(jugador);
            System.out.println(jugador2);
             */
            //empleadosPorDept(conexion);
            salariosempleados(conexion);
            //DepartmentsCRUD gestorDepartamentos = new DepartmentsCRUD(conexion);
            //gestorDepartamentos.insertar("aaaa", "Prueba");
            //gestorDepartamentos.actualizarNombre("aaaa", "Cambio1");
            //gestorDepartamentos.actualizarDepartamento("Cambio1", "bbbb");
            //gestorDepartamentos.eliminarNombre("Cambio1");
            //gestorDepartamentos.eliminarDepartamento("aaaa");
            //gestorDepartamentos.getDepartamentoId("aaaa");
            //gestorDepartamentos.getDepartamentoNombre("Prueba");
            //callable(conexion);
            //callableFunction(conexion);
            //ejecutarFichero(conexion);
            //accesoFuncion(conexion);
            conexion.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
}
