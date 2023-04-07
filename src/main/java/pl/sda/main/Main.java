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

import static java.lang.System.*;


public class Main {
    private static Scanner scanner = new Scanner(in);
    private static EmployeeService employeeService = new EmployeeService();
    private static ClientService clientService = new ClientService();
    private static AddressService addressService = new AddressService();

    public static void main(String[] args) {

        // ********************************DEVELOPMENT TESTS
        Session session = HibernateUtil.getSession();
        Employee employee1 = new Employee("Joanna", "Mickiewicz", "96121536981");
        Client client1 = new Client("Adam", "Spadam", "59874532145");

        Address address1 = new Address("Gołdap", "Sosnowa", "3", "4");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        client1.setAddresses(addressList);


        List<Good> goods = new ArrayList<>();
        Contract contract1 = new Contract(employee1, client1, goods);
        Good good = new Good("applewatch", Category.OTHER_ELECTRONICS, BigDecimal.valueOf(1500), BigDecimal.valueOf(3000), contract1);
        goods.add(good);
        session.getTransaction().begin();
        session.persist(client1);
        session.persist(address1);
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
            System.out.println("21 - Dodaj klienta"); //TODO ver1: sprawdzić czemu nie wyświetla adresów przypisanych do klienta w tabeli address_client
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
                case 0:
                    HibernateUtil.closeSessionFactory();
                    System.exit(0);
                case 11:
                    addEmployee();
                    break;
                case 12:
                    editEmployee();
                    break;
                case 13:
                    deleteEmployeeByPesel();
                    break;
                case 21:
                    addClient();
                    break;
                case 22:
                    editClient();
                    break; //TODO ver1 : Będzie potrzebny refactor. Nieeleganckie są HQLy w tej ilości.
//                case 23: deleteClient(); break;
//                case 31: findContractById(); break;
                default:
                    System.out.println("Nierozpoznana operacja");
                    break;
            }
        } while (true);
    }

    private static void editClient() {
        System.out.println("Podaj PESEL klienta, który ma być edytowany: ");
        String pesel = scanner.next();
        scanner.nextLine();

        while (!clientService.existsByPesel(pesel)) {
            System.out.println("Nie odnaleziono klienta o podanym PESEL. Wpisz poprawny PESEL: ");
            pesel = scanner.next();
            scanner.nextLine();

        }

        if (clientService.existsByPesel(pesel)) {
            System.out.println("Podaj nowe imię lub powtórz poprzednie: ");
            String newName = scanner.next();
            scanner.nextLine();

            System.out.println("Podaj nowe nazwisko lub powtórz poprzednie: ");
            String newSurname = scanner.next();
            scanner.nextLine();

            Session session1 = HibernateUtil.getSession();

            String qrySetName = "UPDATE Employee e SET e.name = '" + newName + "' " +
                    "WHERE pesel = '" + pesel + "' ";
            String qrySetSurname = "UPDATE Employee e SET e.surname = '" + newSurname + "' " +
                    "WHERE pesel = '" + pesel + "' ";
            Query query1 = session1.createQuery(qrySetName);
            Query query2 = session1.createQuery(qrySetSurname);

            session1.getTransaction().begin();
            query1.executeUpdate();
            query2.executeUpdate();
            session1.getTransaction().commit();
            session1.close();


            System.out.println("Czy chcesz edytować adres klienta? Wpisz [T / N]");
            String addressEditDecision = scanner.next();

            if (addressEditDecision.equalsIgnoreCase("T")) {

               System.out.println("TU BĘDZIE KOD ZMIENIAJĄCY ADRES");

//                        sciągnąć klienta hqlem, albo sprytniej metodami z EntityMenager
//                        wyciagnac liste adresow
//                        wyswietlic dostepne adresy z id
//                        scannerem podac wybrane id
//                        uzyc hql j.w. albo sprytniej


//                System.out.println("Podaj nowe miasto lubi wpisz poprzednie: ");
//                String city = scanner.next();
//
//                System.out.println("Podaj nową ulicę: ");
//                String street = scanner.next();
//
//                System.out.println("Podaj nowy  numer domu: ");
//                String houseNum = scanner.next();
//
//                System.out.println("Podaj nowy  numer mieszkania lub wpisz --- by pominąć: ");
//                String flatNum = scanner.next();
//
//                System.out.println("Lista sugerowanych kodów pocztowych: ");
//                addressService.checkPostcode(city);
//
//                System.out.println("Podaj nowy  kod pocztowy w formacie [XX-XXX]: ");
//                String postcode = scanner.next();
//
//                Session session2 = HibernateUtil.getSession();
//
//                String qrySetCity = "UPDATE Address e SET e.city = '" + city + "' " +
//                        "WHERE pesel = '" + pesel + "' ";
//                String qrySetStreet = "UPDATE Address e SET e.street = '" + street + "' " +
//                        "WHERE pesel = '" + pesel + "' ";
//                String qrySetHouseNum = "UPDATE Address e SET e.houseNum = '" + houseNum + "' " +
//                        "WHERE pesel = '" + pesel + "' ";
//                String qrySetFlatNum = "UPDATE Address e SET e.flatNum = '" + flatNum + "' " +
//                        "WHERE pesel = '" + pesel + "' ";
//                String qrySetPostcode = "UPDATE Address e SET e.postcode = '" + postcode + "' " +
//                        "WHERE pesel = '" + pesel + "' ";
//
//                Query query3 = session2.createQuery(qrySetCity);
//                Query query4 = session2.createQuery(qrySetStreet);
//                Query query5 = session2.createQuery(qrySetHouseNum);
//                Query query6 = session2.createQuery(qrySetFlatNum);
//                Query query7 = session2.createQuery(qrySetPostcode);
//
//                session2.getTransaction().begin();
//                query3.executeUpdate();
//                query4.executeUpdate();
//                query5.executeUpdate();
//                query6.executeUpdate();
//                query7.executeUpdate();
//                session2.getTransaction().commit();
//                session2.close();

            } else if (addressEditDecision.equalsIgnoreCase("N")) {
                System.out.println("Zmieniono dane klienta o numerze PESEL: " + pesel);
            } else {
                System.out.println("Nie rozpoznano odpowiedzi. Zmieniono imię i nazwisko klienta o numerze PESEL: " + pesel + ", ale nie adres. ");
            }

        }

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

        System.out.println("Lista sugerowanych kodów pocztowych: ");
        addressService.checkPostcode(city);

        System.out.println("Podaj kod pocztowy w formacie [XX-XXX]: ");
        String postcode = scanner.next(); //TODO ver 2: dodać komunikat jesli nie znaleziono żadnych kodów; jeśli dużo kodów (duże miasta), to dodać ulicę do spr; zmienić format wyświetlania

        System.out.println("Podaj numer telefonu lub wpisz --- by pominąć");
        String phone = scanner.next();

        System.out.println("Podaj email lub wpisz --- by pominąć");
        String email = scanner.next();

        System.out.println("Podaj dodatkowe uwagi lub wpisz --- by pominąć");
        String comments = scanner.next();

        boolean result = clientService.createClientWithAddress(name, surname, pesel, city, street, houseNum, flatNum, postcode, phone, email, comments);

        if (result) {
            System.out.println("Pomyślnie utworzono klienta: " + name + " " + surname);
        } else {
            System.out.println("Nie utworzono klienta: " + name + " " + surname);
        }
    }

    private static void editEmployee() {

        System.out.println("Podaj PESEL pracownika, który ma być edytowany: ");
        String pesel = scanner.next();
        scanner.nextLine();

        while (!employeeService.existsByPesel(pesel)) {
            System.out.println("Nie odnaleziono pracownika o podanym PESEL. Wpisz poprawny PESEL: ");
            pesel = scanner.next();
            scanner.nextLine();
        }

        if (employeeService.existsByPesel(pesel)) {
            System.out.println("Podaj nowe imię: ");
            String newName = scanner.next();
            scanner.nextLine();

            System.out.println("Podaj nowe nazwisko: ");
            String newSurname = scanner.next();
            scanner.nextLine();

            Session session = HibernateUtil.getSession();

            String qrySetName = "UPDATE Employee e SET e.name = '" + newName + "' " +
                    "WHERE pesel = '" + pesel + "' ";
            String qrySetSurname = "UPDATE Employee e SET e.surname = '" + newSurname + "' " +
                    "WHERE pesel = '" + pesel + "' ";
            Query query1 = session.createQuery(qrySetName);
            Query query2 = session.createQuery(qrySetSurname);

            session.getTransaction().begin();
            query1.executeUpdate();
            query2.executeUpdate();
            session.getTransaction().commit();
            session.close();
        }


    }


    private static void deleteEmployeeByPesel() {

        System.out.println("Podaj PESEL usuwanego pracownika");
        String pesel = scanner.next();
        if (employeeService.existsByPesel(pesel)) {
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

        while (employeeService.existsByPesel(pesel)) {
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

