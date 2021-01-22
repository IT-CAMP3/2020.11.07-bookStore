package pl.camp.it.book.store.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.book.store.dao.IOrderPositionDAO;
import pl.camp.it.book.store.model.OrderPosition;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class HibernateOrderPositionDAOImpl implements IOrderPositionDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<OrderPosition> getAllOrderPositions() {
        Session session = this.sessionFactory.openSession();
        Query<OrderPosition> query = session.createQuery("FROM pl.camp.it.book.store.model.OrderPosition");
        List<OrderPosition> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public List<OrderPosition> getOrderPositionsForOrderById(int orderId) {
        Session session = this.sessionFactory.openSession();
        Query<OrderPosition> query =
                session.createQuery("FROM pl.camp.it.book.store.model.OrderPosition WHERE order_id = :orderId");
        query.setParameter("orderId", orderId);
        List<OrderPosition> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public OrderPosition getOrderPositionById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<OrderPosition> query =
                session.createQuery("FROM pl.camp.it.book.store.model.OrderPosition WHERE id = :id");
        query.setParameter("id", id);
        OrderPosition orderPosition = null;
        try {
            orderPosition = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nie znaleziono OrderPosition o id: " + id);
        } finally {
            session.close();
        }
        return orderPosition;
    }

    @Override
    public void removeOrderPosition(OrderPosition orderPosition) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(orderPosition);
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
