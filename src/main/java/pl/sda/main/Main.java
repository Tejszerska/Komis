package pl.sda.main;
import org.hibernate.query.Query;
import pl.sda.entities.*;
import org.hibernate.Session;
import pl.sda.enums.Category;
import pl.sda.service.AddressService;
import pl.sda.service.ClientService;
import pl.sda.service.EmployeeService;
import pl.sda.util.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.in;


public class Main {
    private static Scanner scanner = new Scanner(in);
    private static EmployeeService employeeService = new EmployeeService();
    private static ClientService clientService = new ClientService();
    private static AddressService addressService = new AddressService();

    public static void main(String[] args) {

        // ********************************DEVELOPMENT TESTS
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

//        addressService.checkPostcode("Ełk");
        //*******************************************************


        int choose;

        do {
            System.out.println("Wybierz opcję: ");
            System.out.println("0 - zakończ");
//            System.out.println("DOT. PRACOWNIKA");
//            System.out.println("11 - Dodaj pracownika");
//            System.out.println("12 - Zmień dane pracownika");
//            System.out.println("13 - Usuń pracownika");
            System.out.println("DOT. KLIENTA");
            System.out.println("21 - Dodaj klienta"); //TODO sprawdzić wyświetlanie adresów przypisanych do klienta
            System.out.println("22 - Zmień dane klienta");
            System.out.println("23 - Usuń klienta");
//            System.out.println("DOT. UMOWY");
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
                case 12: editEmployee(); break; 
                case 13: deleteEmployeeByPesel(); break;
                case 21: addClient(); break;
//                case 23: deleteClient(); break;
//                case 31: findContractById(); break;
                default: System.out.println("Nierozpoznana operacja"); break;
            }
        } while (true);
    }

    private static void addClient() {
        System.out.println("Podaj imię: ");
        String name = scanner.next();

        System.out.println("Podaj nazwisko: ");
        String surname = scanner.next();

        System.out.println("Podaj PESEL: ");
        String pesel = scanner.next();

        System.out.println("Podaj miasto: ");
        String city = scanner.next();

        System.out.println("Podaj ulicę: ");
        String street = scanner.next();

        System.out.println("Podaj numer domu: ");
        String houseNum = scanner.next();

        System.out.println("Podaj numer mieszkania lub wpisz --- by pominąć: ");
        String flatNum = scanner.next();

        System.out.println(addressService.checkPostcode(city));

        System.out.println("Podaj numer telefonu lub wpisz --- by pominąć");
        String phone = scanner.next();

        System.out.println("Podaj email lub wpisz --- by pominąć");
        String email = scanner.next();

        System.out.println("Podaj dodatkowe uwagi lub wpisz --- by pominąć");
        String comments = scanner.next();

        boolean result = clientService.createClientWithAddress(name, surname, pesel, city, street, houseNum, flatNum, phone, email, comments);

        if (result) {
            System.out.println("Pomyślnie utworzono klienta: " + name + " " + surname);
        } else {
            System.out.println("Nie utworzono klienta: "  + name + " " + surname);
        }
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
    }

    private static void deleteEmployeeByPesel() {

        System.out.println("Podaj PESEL usuwanego pracownika");
        String pesel = scanner.next();
        if (employeeService.existsByPesel(pesel)){
            Session session = HibernateUtil.getSession();
            Query delete = session.createQuery("DELETE FROM Employee p where p.pesel=:pesel")
                    .setParameter("pesel", pesel);
            session.getTransaction().begin();
            delete.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Pomyślnie usunięto pracownika o PESEL: " + pesel);
        } else {
            System.out.println("Nie odnaleziono pracownika o podanym numerze PESEL");
        }
     }
     
     
    private static void addEmployee() {
        System.out.println("Podaj imię: ");
        String name = scanner.next();

        System.out.println("Podaj nazwisko: ");
        String surname = scanner.next();

        System.out.println("Podaj PESEL: ");
        String pesel = scanner.next();
        
        while(employeeService.existsByPesel(pesel)) {
            System.out.println("Podany PESEL jest przypisany do istniejącego pracownika. Podaj nowy PESEL: ");
            pesel = scanner.next();
        }

        boolean result = employeeService.createEmployeeByPesel(name, surname, pesel);

        if (result) {
            System.out.println("Pomyślnie utworzono pracownika " + name + " " + surname);
        } else {
            System.out.println("Nie utworzono pacjenta o numerze PESEL: " + pesel);
        }
    }
    }

