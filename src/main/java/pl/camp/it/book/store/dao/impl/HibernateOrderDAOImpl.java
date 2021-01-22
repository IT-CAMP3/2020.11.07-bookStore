package pl.camp.it.book.store.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.model.Order;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateOrderDAOImpl implements IOrderDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.camp.it.book.store.model.Order WHERE user_id = :user_id");
        query.setParameter("user_id", userId);
        List<Order> orders = query.getResultList();
        session.close();
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.camp.it.book.store.model.Order");
        List<Order> orders = query.getResultList();
        session.close();
        return orders;
    }

    @Override
    public Order getOrderById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.camp.it.book.store.model.Order WHERE id = :id");
        query.setParameter("id", id);
        Order order = null;
        try {
            order = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono orderu o id " + id);
        } finally {
            session.close();
        }

        return order;
    }

    @Override
    public void deleteOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void updateOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
