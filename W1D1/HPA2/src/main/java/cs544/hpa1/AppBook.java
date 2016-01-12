/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs544.hpa1;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.EntityTransaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author michael
 */
public class AppBook {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

 

    public static void main(String[] args) {
        // Hibernate placeholders

        EntityTransaction tx = null;
        entityManagerFactory = Persistence.createEntityManagerFactory("cs544_HPA2");
        try {
            
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            // Create new instance of Book and set values in it
            Book book1 = new Book("Thinking in Java", "ISBN-9999", "Scott", 100, new Date());
            // save the book
            entityManager.persist(book1);
            // Create new instance of Book and set values in it
            Book book2 = new Book("Effective Java", "ISBN-8888", "James", 80, new Date());
            // save the book
            entityManager.persist(book2);
            
            Book book3 = new Book("Core Java", "ISBN-7777", "Steve", 70, new Date());
            // save the book
            entityManager.persist(book3);

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            // retieve all cars
            @SuppressWarnings("unchecked")
            List<Book> bookList = entityManager.createQuery("select b from Book b").getResultList();
            for (Book book : bookList) {
                System.out.println("title= " + book.getTitle() + ", ISBN= "
                        + book.getISBN()+ ", author= " + book.getAuthor() + ", price= " + book.getPrice() + ", date= " + book.getPublish_date());
            }
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        
        
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            // retieve all cars
            @SuppressWarnings("unchecked")
            Book book = (Book)entityManager.find(Book.class, new Integer(3));
           
            System.out.println("title= " + book.getTitle() + ", ISBN= "
                    + book.getISBN()+ ", author= " + book.getAuthor() + ", price= " + book.getPrice() + ", date= " + book.getPublish_date());
            book.setTitle("Java in a Nutshell");
            book.setPrice(30);
            
            Book rmBook = (Book)entityManager.find(Book.class, new Integer(2));
            entityManager.remove(rmBook);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        // Close the SessionFactory (not mandatory)
        entityManagerFactory.close();
        System.exit(0);
    }
    
}
