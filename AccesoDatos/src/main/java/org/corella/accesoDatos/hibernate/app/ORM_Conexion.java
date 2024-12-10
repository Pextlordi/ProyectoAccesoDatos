package org.corella.accesoDatos.hibernate.app;

import org.corella.accesoDatos.hibernate.entities.Departamento;
import org.corella.accesoDatos.hibernate.entities.Empleado;
import org.corella.accesoDatos.hibernate.entities.EmpleadoDatosProf;
import org.corella.accesoDatos.hibernate.entities.Sede;
import org.corella.accesoDatos.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class ORM_Conexion {
    public void saveEntity() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Empleado empleado = new Empleado();
            empleado.setDni("9876543");
            empleado.setIdDepto(session.get(Departamento.class, 1));
            empleado.setNomEmp("Juan");
            EmpleadoDatosProf empleadoDatosProf = new EmpleadoDatosProf();
            empleadoDatosProf.setDni(empleado.getDni());
            empleadoDatosProf.setCategoria("02");
            empleadoDatosProf.setSueldoBrutoAnual(BigDecimal.valueOf(30000));
            empleado.setDatosProfesionales(empleadoDatosProf);
            session.save(empleado);
            transaccion.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }

    public void getEmpleado() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Empleado empleado = session.get(EmpleadoDatosProf.class, "123456").getEmpleado();
            System.out.println(empleado.getNomEmp());
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }

    public void run() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            //Empleado empleado = session.find(Empleado.class,"123456");
            //Empleado empleado = session.get(Empleado.class, "123456");
            Empleado empleado = session.load(Empleado.class, "123456");
            BigDecimal sueldo = empleado.getDatosProfesionales().getSueldoBrutoAnual();
            System.out.println(sueldo);
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }
}
