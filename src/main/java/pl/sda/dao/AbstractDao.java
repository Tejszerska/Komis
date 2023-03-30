package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.sda.util.HibernateUtil;

//CRUD CREATE READ UPDATE DELETE
public abstract class AbstractDao<T> {

    private Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T t) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();
    }

    public T getById(Integer id) {
        Session session = HibernateUtil.getSession();
        T result = session.get(clazz, id);
        session.close();
        return result;
    }

    public void update(T t) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    public void delete(T t) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
        session.close();
    }

}
