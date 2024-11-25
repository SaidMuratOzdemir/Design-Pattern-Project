package com.designpatterns.dao;


import com.designpatterns.composite.Product;
import com.designpatterns.database.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAO {

    public Product getProductByProductId(String productId) {
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            return session.createQuery("from Product where productId = :productId", Product.class)
                    .setParameter("productId", productId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addProduct(Product product) {
        Transaction transaction = null;
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        Transaction transaction = null;
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        Transaction transaction = null;
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                System.out.println("Ürün silindi: " + product.getName());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        try (Session session = DBConnection.getSessionFactory().openSession()) {
            return session.createQuery("from Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
