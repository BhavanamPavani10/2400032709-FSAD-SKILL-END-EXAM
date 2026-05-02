package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ClientDemo 
{
    @SuppressWarnings("deprecation")
	public static void main(String[] args) 
    {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        // ✅ INSERT (Persistent Object)
        Transport t = new Transport();
        t.setId(1);
        t.setName("Bus");
        t.setDate("2026-05-02");
        t.setStatus("Available");
        t.setType("Public");
        t.setCost(50.0);

        session.save(t);

        tx.commit();

        System.out.println("Transport Inserted Successfully");

        // ✅ HQL - VIEW ALL (No WHERE)
        session.beginTransaction();

        String hql = "from Transport t where t.id >= :val";
        Query<Transport> query = session.createQuery(hql, Transport.class);
        query.setParameter("val", 0);   // named parameter

        List<Transport> list = query.list();

        for(Transport tr : list)
        {
            System.out.println(tr.getId()+" "+tr.getName()+" "+tr.getStatus());
        }

        session.close();
        sf.close();
    }
}