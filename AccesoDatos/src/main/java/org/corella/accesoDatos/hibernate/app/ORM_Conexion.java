package org.corella.accesoDatos.hibernate.app;

import org.corella.accesoDatos.hibernate.entities.Departamento;
import org.corella.accesoDatos.hibernate.entities.Empleado;
import org.corella.accesoDatos.hibernate.entities.EmpleadoDatosProf;
import org.corella.accesoDatos.hibernate.entities.Sede;
import org.corella.accesoDatos.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
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

    public void queryMethod() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Query consulta = session.createQuery("FROM Sede sede where nomSede like '%k%'").setReadOnly(true);
            Sede sede = (Sede) consulta.getSingleResult();
            System.out.println(sede.getNomSede());
            Query consultaUpdate = session.createQuery("UPDATE Sede sede SET nomSede = 'New York' WHERE nomSede = 'Dublin'");
            int output = consultaUpdate.executeUpdate();
            System.out.println(output);
            Query consultaParam = session.createQuery("FROM Sede sede where nomSede like :param");
            consultaParam.setParameter("param", "%o");
            System.out.println("Conjunto");
            for (Object res : consultaParam.getResultList()) {
                Sede sedeRes = (Sede) res;
                System.out.println(sedeRes.getNomSede());
            }
        }
    }


    public void run() {
        queryMethod();
    }
}
