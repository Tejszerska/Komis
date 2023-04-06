package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.sda.entities.Client;
import pl.sda.entities.Employee;
import pl.sda.util.HibernateUtil;

import java.util.List;

public class ClientDao extends AbstractDao{
    public ClientDao() {
        super(Client.class);
    }

    public boolean existsByPesel(String pesel) {
        return getByPesel(pesel) == null ? false : true;

    }
    private Client getByPesel(String pesel) {
        String hql = "FROM Client WHERE pesel = :p1";
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery(hql);
        query.setParameter("p1", pesel);

        List<Client> resultList = query.getResultList();
        session.close();

        return resultList
                .stream()
                .findFirst()
                .orElse(null);
    }
    }

