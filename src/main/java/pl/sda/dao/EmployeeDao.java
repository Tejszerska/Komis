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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/komis?serverTimezone=Europe/Warsaw", "root", "kijanka321");

            Statement statement = connection.createStatement();
            statement.execute("UPDATE employee SET name = " + name);
            statement.execute("UPDATE employee SET surname = " + surname);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }

}
