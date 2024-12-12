package org.corella.accesoDatos.hibernate.app;

import org.corella.accesoDatos.hibernate.entities.Departamento;
import org.corella.accesoDatos.hibernate.entities.Empleado;
import org.corella.accesoDatos.hibernate.entities.EmpleadoDatosProf;
import org.corella.accesoDatos.hibernate.entities.Sede;
import org.corella.accesoDatos.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

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
            session.save(empleado);
            Empleado encontrado = session.get(Empleado.class, empleado.getDni());
            encontrado.setDatosProfesionales(empleadoDatosProf);
            session.update(encontrado);
            transaccion.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }

    public void buscarDepartamentos() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Sede sede = session.get(Sede.class, 3);
            Set<Departamento> listaDepartamentos = sede.getDepartamentosSede();
            for (Departamento departamento : listaDepartamentos) {
                System.out.println(departamento.getNomDepto());
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void insertarSede() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Sede nuevaSede = new Sede();
            nuevaSede.setNomSede("Helsinki");
            session.save(nuevaSede);

            Departamento nuevoDepartamento = new Departamento("QA", nuevaSede);
            session.save(nuevoDepartamento);
            nuevoDepartamento = new Departamento("Marketing", nuevaSede);
            session.save(nuevoDepartamento);
            session.refresh(nuevaSede);

            if (nuevaSede.getDepartamentosSede() != null) {
                for (Departamento departamento : nuevaSede.getDepartamentosSede()) {
                    System.out.println(departamento.getNomDepto());
                }
            } else {
                transaccion.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void getEmpleado() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Empleado empleado = session.get(Empleado.class, "123456");
            System.out.println(empleado.getDatosProfesionales().getSueldoBrutoAnual());
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }

    public void run() {
        insertarSede();
    }
}
