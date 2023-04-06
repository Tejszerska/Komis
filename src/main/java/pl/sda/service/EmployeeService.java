package pl.sda.service;

import pl.sda.dao.EmployeeDao;
import pl.sda.entities.Employee;

public class EmployeeService {
    private EmployeeDao employeeDao = new EmployeeDao();

    public boolean createEmployeeByPesel(String name, String surname, String pesel) {
        if (!employeeDao.existsByPesel(pesel)) {
            Employee employee = new Employee(name, surname, pesel);
            employeeDao.save(employee);
            return true;
        }
        return false;
    }

    public Employee getByPesel(String pesel) {
        return employeeDao.getByPesel(pesel);
    }

    public boolean existsByPesel(String pesel) {
        return employeeDao.existsByPesel(pesel);
    }
}



