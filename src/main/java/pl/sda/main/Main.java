package pl.sda.main;

import org.hibernate.query.Query;
import pl.sda.dao.EmployeeDao;
import pl.sda.entities.*;
import org.hibernate.Session;
import pl.sda.enums.Category;
import pl.sda.service.EmployeeService;
import pl.sda.util.HibernateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    private static Scanner scanner = new Scanner(in);
    private static EmployeeService employeeService = new EmployeeService();
    private  static EmployeeDao employeeDao = new EmployeeDao();

    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        Employee employee1 = new Employee("Joanna", "Mickiewicz", "96121536981");
        Client client = new Client("Adam", "Spadam", "59874532145");
        Address address = new Address("Gołdap", "Sosnowa", "3", "4");

        List <Good> goods = new ArrayList<>();
        Contract contract1 = new Contract(employee1, client, goods);
        Good good = new Good("applewatch", Category.OTHER_ELECTRONICS, BigDecimal.valueOf(1500) , BigDecimal.valueOf(3000), contract1);
        goods.add(good);
        session.getTransaction().begin();
        session.persist(client);
        session.persist(address);
        session.persist(contract1);
        session.persist(employee1);
        session.persist(good);
        session.getTransaction().commit();
        session.close();
        //*******************************************************


        int choose;

        do {
            System.out.println("Wybierz opcję: ");
            System.out.println("0 - zakończ");
            System.out.println("DOT. PRACOWNIKA");
            System.out.println("11 - Dodaj pracownika");
            System.out.println("12 - Zmień dane pracownika"); //TODO
            System.out.println("13 - Usuń pracownika");
//            System.out.println("DOT. KLIENTA");
//            System.out.println("21 - Dodaj klienta");
//            System.out.println("22 - Zmień dane klienta"); //TODO
//            System.out.println("23 - Usuń klienta");
//            System.out.println("DOT. UMOWY");
//            System.out.println("21 - Dodaj umowę");
//            System.out.println("22 - anuluj umowę");
//            System.out.println("DOT. TOWARU");
//            System.out.println("21 - Dodaj umowę");
//            System.out.println("22 - anuluj umowę");
//            System.out.println("DOT. WYSZUKIWANIA");
//            System.out.println("31 - Wyszukaj umowę po jej numerze");
//            System.out.println("32 - Wyszukaj klienta po PESEL");
//            System.out.println("33 - Wyszukaj wszystkie towary");


            choose = scanner.nextInt();

            switch (choose) {
                case 0: HibernateUtil.closeSessionFactory(); System.exit(0);
                case 11: addEmployee(); break;
                case 12: editEmployee(); break; //TODO
                case 13: deleteEmployeeByPesel(); break;
//                case 21: addClient(); break;
//                case 23: deleteClient(); break;
//                case 31: findContractById(); break;
                default: System.out.println("Nierozpoznana operacja"); break;
            }
        } while (true);
    }

    private static void editEmployee() {

        System.out.println("Podaj PESEL pracownika, który ma być edytowany: ");
        String newPesel = scanner.next();
        scanner.nextLine();

        System.out.println("Podaj nowe imię: ");
        String newName = scanner.next();
        scanner.nextLine();

        System.out.println("Podaj nowe nazwisko: ");
        String newSurname = scanner.next();
        scanner.nextLine();

        Session session = HibernateUtil.getSession();
        System.out.println("Simple update");
        String qrySetName = "UPDATE Employee e SET e.name = '" + newName + "' "+
                "WHERE pesel = '" + newPesel + "' ";
        String qrySetSurname = "UPDATE Employee e SET e.surname = '" + newSurname + "' "+
                "WHERE pesel = '" + newPesel + "' ";
        Query query1 = session.createQuery(qrySetName);
        Query query2 = session.createQuery(qrySetSurname);

        session.getTransaction().begin();
        query1.executeUpdate();
        query2.executeUpdate();
        session.getTransaction().commit();
        session.close();



//        Query query1 = session.createQuery("FROM Employee WHERE pesel = '" + newPesel + "' ");
//        Query query2 = session.createQuery("UPDATE Employee e SET e.name = '" + newName + "' "+
//                "WHERE pesel = '" + newPesel + "' ");
//
//        query2.executeUpdate();



        System.out.println("Nie zaktualizowano bazy ;/ na później");
        session.close();

    }

    private static void deleteEmployeeByPesel() {

        System.out.println("Podaj PESEL usuwanego pracownika");
        String pesel = scanner.next();

        Session session = HibernateUtil.getSession();
        Query delete = session.createQuery("DELETE FROM Employee p where p.pesel=:pesel")
                .setParameter("pesel", pesel);
        session.getTransaction().begin();
        delete.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Pomyślnie usunięto pracownika o PESEL: " + pesel);
    }


    private static void addEmployee() {
        System.out.println("Podaj imię: ");
        String name = scanner.next();

        System.out.println("Podaj nazwisko: ");
        String surname = scanner.next();

        System.out.println("Podaj PESEL: ");
        String pesel = scanner.next();


        boolean result = employeeService.createEmployeeByPesel(name, surname, pesel);

        if (result) {
            System.out.println("Pomyślnie utworzono pracownika " + name + " " + surname);
        } else {
            System.out.println("Nie utworzono pacjenta o numerze PESEL: " + pesel);
        }
    }
    }

