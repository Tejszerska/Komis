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

    public boolean editEmployeeByPesel(String name, String surname, String pesel) {

        if(employeeDao.existsByPesel(pesel)){
            employeeDao.setNameSurname;
        }
    }
}



