package pl.sda.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.sda.entities.Employee;
import pl.sda.util.HibernateUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeDao extends AbstractDao {
    public EmployeeDao() {
        super(Employee.class);
    }

    public boolean existsByPesel(String pesel) {
        return getByPesel(pesel) == null ? false : true;
    }

    private Employee getByPesel(String pesel) {
        String hql = "FROM Employee WHERE pesel = :p1";
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery(hql);
        query.setParameter("p1", pesel);

        List<Employee> resultList = query.getResultList();
        session.close();

        return resultList
                .stream()
                .findFirst()
                .orElse(null);
        }

    }



