package com.designpatterns.dao;


import com.designpatterns.database.HibernateUtil;
import com.designpatterns.layout.WarehouseLayout;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class WarehouseLayoutDAO {

    public void addWarehouseLayout(WarehouseLayout layout) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(layout);
            transaction.commit();
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Bu isimde zaten bir depo düzeni mevcut.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateWarehouseLayout(WarehouseLayout layout) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(layout);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteWarehouseLayout(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            WarehouseLayout layout = session.get(WarehouseLayout.class, id);
            if (layout != null) {
                session.delete(layout);
                System.out.println("Depo düzeni silindi: " + layout.getLayoutName());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public WarehouseLayout getWarehouseLayoutById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(WarehouseLayout.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WarehouseLayout> getAllWarehouseLayouts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from WarehouseLayout", WarehouseLayout.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

