/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs544.hpa1;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author michael
 */
public class AppBook {
     private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Create new instance of Book and set values in it
            Book book1 = new Book("Thinking in Java", "ISBN-9999", "Scott", 100, new Date());
            // save the book
            session.persist(book1);
            // Create new instance of Book and set values in it
            Book book2 = new Book("Effective Java", "ISBN-8888", "James", 80, new Date());
            // save the book
            session.persist(book2);
            
            Book book3 = new Book("Core Java", "ISBN-7777", "Steve", 70, new Date());
            // save the book
            session.persist(book3);

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // retieve all cars
            @SuppressWarnings("unchecked")
            List<Book> bookList = session.createQuery("from Book").list();
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
            if (session != null) {
                session.close();
            }
        }
        
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // retieve all cars
            @SuppressWarnings("unchecked")
            Book book = (Book)session.get(Book.class, new Integer(3));
           
            System.out.println("title= " + book.getTitle() + ", ISBN= "
                    + book.getISBN()+ ", author= " + book.getAuthor() + ", price= " + book.getPrice() + ", date= " + book.getPublish_date());
            book.setTitle("Java in a Nutshell");
            book.setPrice(30);
            
            Book rmBook = (Book)session.get(Book.class, new Integer(2));
            session.delete(rmBook);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }
    
}
