package org.corella.accesoDatos.hibernate.app;

import org.corella.accesoDatos.hibernate.entities.Sede;
import org.corella.accesoDatos.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ORM_Conexion {
    public void run() {
        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            Sede sede = new Sede();
            sede.setNomSede("Sydney");
            session.save(sede);
            transaccion.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaccion != null) {
                transaccion.rollback();
            }
        }
    }
}
