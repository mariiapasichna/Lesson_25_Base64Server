package com.mariiapasichna.dao;

import com.mariiapasichna.Configurations;
import com.mariiapasichna.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ImageDao {

    private SessionFactory sessionFactory;

    public ImageDao() {
        sessionFactory = Configurations.getConfigurations();
    }

    public Image addImage(Image image) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(image);
            transaction.commit();
            return image;
        }
    }

    public List<Image> getImage(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM Image WHERE id = :id ", Image.class)
                    .setParameter("id", id)
                    .list();
        }
    }

    public List<Image> getImages() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Image", Image.class).list();
        }
    }
}